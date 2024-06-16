package com.example.controller;

import com.example.application.ProductService;
import com.example.controller.dto.request.CreateProductRequest;
import com.example.controller.dto.response.ProductResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/products")
@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public Long create(@RequestBody CreateProductRequest request) {
        return productService.create(request.name(), request.price(), request.quantity());
    }

    @GetMapping
    public List<ProductResponse> getAll() {
        return productService.getAll().stream()
                .map(ProductResponse::toProductResponse)
                .toList();
    }

    @GetMapping("/{productId}")
    public ProductResponse getBy(@PathVariable("productId") Long productId) {
        return ProductResponse.toProductResponse(productService.getBy(productId));
    }
}
