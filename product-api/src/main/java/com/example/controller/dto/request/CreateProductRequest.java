package com.example.controller.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record CreateProductRequest(
        @NotBlank(message = "상품명를 입력해주세요.")
        String name,

        @DecimalMin(value = "0", message = "가격은 0원 이상 입니다.")
        BigDecimal price,

        @PositiveOrZero(message = "상품 갯수를 입력해주세요.")
        int quantity
) {
}
