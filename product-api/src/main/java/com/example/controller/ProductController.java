package com.example.controller;

import com.example.application.ProductService;
import com.example.controller.dto.request.CreateProductRequest;
import com.example.controller.dto.request.ProductDecreaseRequest;
import com.example.controller.dto.response.ProductResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/products")
@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Long create(@Valid @RequestBody CreateProductRequest request) {
        log.info("create");
        return productService.create(request.name(), request.price(), request.quantity());
    }

    @PostMapping("/{productId}/decrease")
    public void decrease(@PathVariable Long productId, @RequestBody ProductDecreaseRequest request) {
        productService.decrease(productId, request.quantity());
    }

    @GetMapping
    public List<ProductResponse> getAll() {
        log.info("getAll");
        return productService.getAll().stream()
                .map(ProductResponse::toProductResponse)
                .toList();
    }

    @GetMapping("/{productId}")
    public ProductResponse getBy(@PathVariable Long productId) {
        return ProductResponse.toProductResponse(productService.getBy(productId));
    }

    @GetMapping("/test")
    public void test() {
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            throw new RuntimeException("circuit breaker test");
        }
    }
}
