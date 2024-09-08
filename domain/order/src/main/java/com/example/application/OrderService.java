package com.example.application;

import com.example.annotation.DistributedLock;
import com.example.application.dto.OrderDto;
import com.example.application.event.OrderEventDto;
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
import com.example.repository.RedisSetRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class OrderService {

    private final ProductClient productClient;
    private final PaymentClient paymentClient;
    private final RedisSetRepository redisSetRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ApplicationEventPublisher publisher;

    public OrderService(ProductClient productClient, PaymentClient paymentClient, RedisSetRepository redisSetRepository, OrderRepository orderRepository, OrderDetailRepository orderDetailRepository, ApplicationEventPublisher publisher) {
        this.productClient = productClient;
        this.paymentClient = paymentClient;
        this.redisSetRepository = redisSetRepository;
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.publisher = publisher;
    }

    @Transactional
    public String order(Long productId, int quantity, Long memberId, String token) {
        // 상품 재고 확인
//        Product product = productRepository.getLockBy(productId);
//        product.checkQuantity(quantity);

        ProductResponse productResponse = productClient.getBy(productId, token);
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
    public String orderByRedisLock(Long productId, int quantity, Long memberId, String token) {
        // 상품 재고 확인
        ProductResponse productResponse = productClient.getBy(productId, token);
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
    public String orderByRabbitmq(Long productId, int quantity, Long memberId, String token) {
        // 상품 재고 확인
        ProductResponse productResponse = productClient.getBy(productId, token);

        String orderCode = OrderCodeSequence.create(LocalDateTime.now());
        int decrementQuantity = productResponse.quantity();
        String code = redisSetRepository.validQuantity("order-" + productId, orderCode, decrementQuantity);

        if (code.equals("3")) {
            throw new IllegalStateException("재고 수량이 부족합니다.");
        }

        BigDecimal price = productResponse.price();
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

        publisher.publishEvent(new OrderEventDto(orderCode, token));

        return orderCode;
    }

    public OrderDto getBy(String orderCode) {
        Order order = orderRepository.getBy(orderCode);
        OrderDetail orderDetail = orderDetailRepository.getBy(order.getId());

        return new OrderDto(orderDetail.getProductId(), orderDetail.getQuantity(), orderDetail.getPrice());
    }
}
