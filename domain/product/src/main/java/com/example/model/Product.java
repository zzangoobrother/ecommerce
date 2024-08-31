package com.example.model;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class Product {

    private Long id;
    private String name;
    private BigDecimal price;
    private int quantity;

    @Builder
    public Product(Long id, String name, BigDecimal price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public void checkQuantity(int quantity) {
        if (this.quantity - quantity < 0) {
            throw new IllegalStateException("재고 수량이 부족합니다.");
        }
    }

    public void deducted(int quantity) {
        this.quantity -= quantity;
    }
}
