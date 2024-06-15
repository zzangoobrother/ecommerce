package com.example.controller;

import com.example.application.ProductService;
import com.example.controller.dto.request.CreateProductRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/products")
    public Long create(@RequestBody CreateProductRequest request) {
        return productService.create(request.name(), request.price(), request.quantity());
    }
}
