package com.example.controller;

import com.example.controller.dto.request.CreateProductRequest;
import com.example.controller.dto.request.ProductDecreaseRequest;
import com.example.controller.dto.response.ProductResponse;
import com.example.facade.ProductFacade;
import com.example.global.config.auth.AuthMember;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/products")
@RestController
public class ProductController {

    private final ProductFacade productFacade;

    public ProductController(ProductFacade productFacade) {
        this.productFacade = productFacade;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Long create(@Valid @RequestBody CreateProductRequest request) {
        log.info("create");
        return productFacade.create(request.name(), request.price(), request.quantity());
    }

    @PostMapping("/{productId}/decrease")
    public void decrease(@PathVariable Long productId, @RequestBody ProductDecreaseRequest request) {
        productFacade.decrease(productId, request.quantity());
    }

    @GetMapping
    public List<ProductResponse> getAll() {
        log.info("getAll");
        return productFacade.getAll().stream()
                .map(ProductResponse::toProductResponse)
                .toList();
    }

    @GetMapping("/{productId}")
    public ProductResponse getBy(@PathVariable Long productId, HttpServletRequest request) {
        String token = request.getHeader("queue-token");
        return ProductResponse.toProductResponse(productFacade.getBy(productId, token));
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
