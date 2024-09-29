package com.example.application;

import com.example.client.FakeApplicationEventPublisher;
import com.example.client.FakeOrderClient;
import com.example.client.OrderClient;
import com.example.model.Payment;
import com.example.repository.InMemoryPaymentRepository;
import com.example.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class PaymentServiceTest {

    private PaymentService paymentService;
    private PaymentRepository paymentRepository;

    private ApplicationEventPublisher applicationEventPublisher;

    private OrderClient orderClient;

    private String orderCode = "20240903213111";
    private BigDecimal price = BigDecimal.valueOf(1000);

    @BeforeEach
    void setUp() {
        paymentRepository = new InMemoryPaymentRepository();
        applicationEventPublisher = new FakeApplicationEventPublisher();
        orderClient = new FakeOrderClient();
        paymentService = new PaymentService(paymentRepository, orderClient, applicationEventPublisher);
    }

    @Test
    void 주문코드_가격_파라미터_결제하기() {
        paymentService.payment(orderCode, price);

        Payment payment = paymentRepository.getBy(orderCode);

        assertAll(
                () -> assertThat(payment.getOrdersCode()).isEqualTo(orderCode),
                () -> assertThat(payment.getPrice()).isEqualTo(price),
                () -> assertThat(payment.getStatus()).isEqualTo(Payment.Status.SUCCESS)
        );
    }

    @Test
    void 주문코드_파라미터_결제하기() {
        paymentService.payment(orderCode);

        Payment payment = paymentRepository.getBy(orderCode);

        assertAll(
                () -> assertThat(payment.getOrdersCode()).isEqualTo(orderCode),
                () -> assertThat(payment.getPrice()).isEqualTo(price),
                () -> assertThat(payment.getStatus()).isEqualTo(Payment.Status.SUCCESS)
        );
    }

    @Test
    void 주문_취소() {
        paymentService.payment(orderCode, price);

        paymentService.cancel(orderCode);

        Payment payment = paymentRepository.getBy(orderCode);

        assertAll(
                () -> assertThat(payment.getOrdersCode()).isEqualTo(orderCode),
                () -> assertThat(payment.getPrice()).isEqualTo(price),
                () -> assertThat(payment.getStatus()).isEqualTo(Payment.Status.CANCEL)
        );
    }

    @Test
    void 주문_취소시_주문_상태가_취소이면_에러() {
        paymentService.payment(orderCode, price);

        paymentService.cancel(orderCode);

        assertThatThrownBy(() -> paymentService.cancel(orderCode))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("주문 취소된 결제 건 입니다.");
    }
}
