package com.example.application;

import com.example.client.*;
import com.example.model.Order;
import com.example.model.OrderDetail;
import com.example.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class OrderServiceTest {

    private OrderService orderService;

    private OrderRepository orderRepository;
    private OrderDetailRepository orderDetailRepository;
    private OrderEventRepository orderEventRepository;

    private ProductClient productClient;
    private PaymentClient paymentClient;

    private RedisSetRepository redisSetRepository;
    private ApplicationEventPublisher applicationEventPublisher;

    @BeforeEach
    void setUp() {
        orderRepository = new InMemoryOrderRepository();
        orderDetailRepository = new InMemoryOrderDetailRepository();
        orderEventRepository = new InMemoryOrderEventRepository();

        productClient = new FakeProductClient();
        paymentClient = new FakePaymentClient();

        redisSetRepository = new FakeRedisSetRepository();
        applicationEventPublisher = new FakeApplicationEventPublisher();

        orderService = new OrderService(productClient, paymentClient, redisSetRepository, orderRepository, orderDetailRepository, applicationEventPublisher);
    }

    @Test
    void db_lock_사용_주문하기() {
        String orderCode = orderService.order(1L, 1, 1L);

        Order order = orderRepository.getBy(orderCode);
        assertAll(
                () -> assertThat(order.getMemberId()).isEqualTo(1L),
                () -> assertThat(order.getOrdersCode()).isEqualTo(orderCode)
        );

        OrderDetail orderDetail = orderDetailRepository.getBy(order.getId());
        assertAll(
                () -> assertThat(orderDetail.getOrdersId()).isEqualTo(1L),
                () -> assertThat(orderDetail.getProductId()).isEqualTo(1L),
                () -> assertThat(orderDetail.getPrice()).isEqualTo(BigDecimal.valueOf(1000)),
                () -> assertThat(orderDetail.getQuantity()).isEqualTo(1)
        );
    }

    @Test
    void db_lock_주문하기_재고_부족시_에러() {
        assertThatThrownBy(() -> orderService.order(1L, 4, 1L))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("재고 수량이 부족합니다.");
    }
}
