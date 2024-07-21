package com.example.application.event;

import com.example.application.dto.PaymentDto;
import com.example.application.dto.ProductDecreaseDto;
import lombok.Getter;

@Getter
public class OrderEvent {

    private final PaymentDto paymentDto;
    private final ProductDecreaseDto productDecreaseDto;

    public OrderEvent(PaymentDto paymentDto, ProductDecreaseDto productDecreaseDto) {
        this.paymentDto = paymentDto;
        this.productDecreaseDto = productDecreaseDto;
    }
}
