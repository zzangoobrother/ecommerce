package com.example.application;

import com.example.model.Order;
import com.example.model.OrderDetail;
import com.example.model.Product;
import com.example.repository.OrderDetailRepository;
import com.example.repository.OrderRepository;
import com.example.repository.PaymentRepository;
import com.example.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class OrderService {

    private final ProductRepository productRepository;
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    public OrderService(ProductRepository productRepository, PaymentRepository paymentRepository, OrderRepository orderRepository, OrderDetailRepository orderDetailRepository) {
        this.productRepository = productRepository;
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    @Transactional
    public String order(Long productId, int quantity, Long memberId) {
        // 상품 재고 확인
        Product product = productRepository.getLockBy(productId);
        product.checkQuantity(quantity);

        // 결제하기
        // 이 계산 로직은 어디에 있는게 좋을까?
        // 상품? 결제? 주문?
        BigDecimal totalPrice = product.getPrice().multiply(BigDecimal.valueOf(quantity));
        paymentRepository.payment(totalPrice);

        // 주문서 생성
        Order order = Order.builder()
                .memberId(memberId)
                .ordersCode(OrderCodeSequence.create(LocalDateTime.now()))
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

        product.deducted(quantity);
        productRepository.save(product);

        return order.getOrdersCode();
    }
}
