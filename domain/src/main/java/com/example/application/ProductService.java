package com.example.application;

import com.example.model.Product;
import com.example.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Long create(String name, BigDecimal price, int quantity) {
        Product product = Product.builder()
                .name(name)
                .price(price)
                .quantity(quantity)
                .build();
        Product saveProduct = productRepository.save(product);
        return saveProduct.getId();
    }
}