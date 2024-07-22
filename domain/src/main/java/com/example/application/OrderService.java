package com.example.application;

import com.example.RabbitmqClient;
import com.example.annotation.DistributedLock;
import com.example.application.dto.OrderDto;
import com.example.application.dto.PaymentCancelDto;
import com.example.application.event.OrderEvent;
import com.example.client.PaymentClient;
import com.example.client.ProductClient;
import com.example.client.dto.request.PaymentCancelRequest;
import com.example.client.dto.request.PaymentRequest;
import com.example.client.dto.request.ProductDecreaseRequest;
import com.example.client.dto.response.ProductResponse;
import com.example.model.Order;
import com.example.model.OrderDetail;
import com.example.repository.OrderDetailRepository;
import com.example.repository.OrderRepository;
import com.example.repository.RedisRepository;
import com.example.repository.RedisSetRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class OrderService {
    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${rabbitmq.routing.payment.cancel.key}")
    private String routingPaymentCancelKey;

    private final ProductClient productClient;
    private final PaymentClient paymentClient;
    private final RabbitmqClient rabbitmqClient;
    private final RedisRepository redisRepository;
    private final RedisSetRepository redisSetRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ApplicationEventPublisher publisher;

    public OrderService(ProductClient productClient, PaymentClient paymentClient, RabbitmqClient rabbitmqClient, RedisRepository redisRepository, RedisSetRepository redisSetRepository, OrderRepository orderRepository, OrderDetailRepository orderDetailRepository, ApplicationEventPublisher publisher) {
        this.productClient = productClient;
        this.paymentClient = paymentClient;
        this.rabbitmqClient = rabbitmqClient;
        this.redisRepository = redisRepository;
        this.redisSetRepository = redisSetRepository;
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.publisher = publisher;
    }

    @Transactional
    public String order(Long productId, int quantity, Long memberId) {
        // 상품 재고 확인
//        Product product = productRepository.getLockBy(productId);
//        product.checkQuantity(quantity);

        ProductResponse productResponse = productClient.getBy(productId);
        if (productResponse.quantity() - quantity < 0) {
            throw new IllegalStateException("재고 수량이 부족합니다.");
        }

        // 결제하기
        // 이 계산 로직은 어디에 있는게 좋을까?
        // 상품? 결제? 주문?
        String orderCode = OrderCodeSequence.create(LocalDateTime.now());
        BigDecimal totalPrice = productResponse.price().multiply(BigDecimal.valueOf(quantity));
        paymentClient.payment(new PaymentRequest(orderCode, totalPrice));

        // 주문서 생성
        Order order = Order.builder()
                .memberId(memberId)
                .ordersCode(orderCode)
                .build();
        Order createOrder = orderRepository.save(order);

        OrderDetail orderDetail = OrderDetail.builder()
                .ordersId(createOrder.getId())
                .productId(productId)
                .quantity(quantity)
                .price(totalPrice)
                .status(OrderDetail.OrderStatus.COMPLETE)
                .build();
        orderDetailRepository.save(orderDetail);

        productClient.decrease(productId, new ProductDecreaseRequest(quantity));

        return order.getOrdersCode();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @DistributedLock(key = "#productId")
    public String orderByRedisLock(Long productId, int quantity, Long memberId) {
        // 상품 재고 확인
        ProductResponse productResponse = productClient.getBy(productId);
        if (productResponse.quantity() - quantity < 0) {
            throw new IllegalStateException("재고 수량이 부족합니다.");
        }

        String orderCode = OrderCodeSequence.create(LocalDateTime.now());

        try {
            // 결제하기
            BigDecimal totalPrice = productResponse.price().multiply(BigDecimal.valueOf(quantity));
            paymentClient.payment(new PaymentRequest(orderCode, totalPrice));

            // 주문서 생성
            Order order = Order.builder()
                    .memberId(memberId)
                    .ordersCode(orderCode)
                    .build();
            Order createOrder = orderRepository.save(order);

            OrderDetail orderDetail = OrderDetail.builder()
                    .ordersId(createOrder.getId())
                    .productId(productId)
                    .quantity(quantity)
                    .price(totalPrice)
                    .status(OrderDetail.OrderStatus.COMPLETE)
                    .build();
            orderDetailRepository.save(orderDetail);

            productClient.decrease(productId, new ProductDecreaseRequest(quantity));

            return order.getOrdersCode();
        } catch (RuntimeException e) {
            paymentClient.cancel(new PaymentCancelRequest(orderCode));
        }

        return null;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public String orderByRabbitmq(Long productId, int quantity, Long memberId) {
        // 상품 재고 확인
        int decrementQuantity;
        BigDecimal price;

        String o = redisRepository.get("product-" + productId);
        ObjectMapper mapper = new ObjectMapper();
        try {
            ProductResponse productResponse = mapper.readValue(o, ProductResponse.class);
            if (Objects.isNull(productResponse)) {
                productResponse = productClient.getBy(productId);
            }

            decrementQuantity = productResponse.quantity();
            price = productResponse.price();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Long size = redisSetRepository.size("order-" + productId);

        if (decrementQuantity - quantity <= size) {
            throw new IllegalStateException("재고 수량이 부족합니다.");
        }

        String orderCode = OrderCodeSequence.create(LocalDateTime.now());
        redisSetRepository.add("order-" + productId, orderCode);

        try {
            // 결제하기
            BigDecimal totalPrice = price.multiply(BigDecimal.valueOf(quantity));

            // 주문서 생성
            Order order = Order.builder()
                    .memberId(memberId)
                    .ordersCode(orderCode)
                    .build();
            Order createOrder = orderRepository.save(order);

            OrderDetail orderDetail = OrderDetail.builder()
                    .ordersId(createOrder.getId())
                    .productId(productId)
                    .quantity(quantity)
                    .price(totalPrice)
                    .status(OrderDetail.OrderStatus.COMPLETE)
                    .build();
            orderDetailRepository.save(orderDetail);

            publisher.publishEvent(new OrderEvent(orderCode));

            return order.getOrdersCode();
        } catch (RuntimeException e) {
            PaymentCancelDto paymentCancelDto = new PaymentCancelDto(orderCode);
            rabbitmqClient.send(exchangeName, routingPaymentCancelKey, paymentCancelDto);
            redisSetRepository.remove("order-" + productId, orderCode);
        }

        return null;
    }

    public OrderDto getBy(String orderCode) {
        Order order = orderRepository.getBy(orderCode);
        OrderDetail orderDetail = orderDetailRepository.getBy(order.getId());

        return new OrderDto(orderDetail.getProductId(), orderDetail.getQuantity(), orderDetail.getPrice());
    }
}
