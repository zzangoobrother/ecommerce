package com.example.facade;

import com.example.application.ProductService;
import com.example.application.DbQueueService;
import com.example.application.dto.ProductDomainResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class ProductFacade {

    private final ProductService productService;
    private final DbQueueService queueService;

    public ProductFacade(ProductService productService, DbQueueService dbQueueService) {
        this.productService = productService;
        this.queueService = dbQueueService;
    }

    public Long create(String name, BigDecimal price, int quantity) {
        return productService.create(name, price, quantity);
    }

    public void decrease(Long productId, int quantity) {
        productService.decrease(productId, quantity);
    }

    public List<ProductDomainResponse> getAll() {
        return productService.getAll();
    }

    /**
     * 1. token을 통해 queue 상태 검증
     * 2. 상품 상세 정보 조회 리턴
     */
    public ProductDomainResponse getBy(Long productId, String token) {
        queueService.checkedByProcessing(token);
        return productService.getBy(productId);
    }
}
