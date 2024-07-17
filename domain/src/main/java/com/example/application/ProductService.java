package com.example.application;

import com.example.application.dto.ProductDomainResponse;
import com.example.model.Product;
import com.example.repository.ProductRepository;
import com.example.repository.RedisCountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final RedisCountRepository redisCountRepository;

    public ProductService(ProductRepository productRepository, RedisCountRepository redisCountRepository) {
        this.productRepository = productRepository;
        this.redisCountRepository = redisCountRepository;
    }

    public Long create(String name, BigDecimal price, int quantity) {
        if (productRepository.existsBy(name)) {
            throw new IllegalArgumentException("중복된 상품명을 가질 수 없습니다.");
        }

        Product product = Product.builder()
                .name(name)
                .price(price)
                .quantity(quantity)
                .build();
        Product saveProduct = productRepository.save(product);

        redisCountRepository.add("product-" + saveProduct.getId(), (long) quantity);

        return saveProduct.getId();
    }

    public List<ProductDomainResponse> getAll() {
        return productRepository.getAll().stream()
                .map(ProductDomainResponse::toProductDomainResponse)
                .toList();
    }

    public ProductDomainResponse getBy(Long productId) {
        Product product = productRepository.getBy(productId);
        return ProductDomainResponse.toProductDomainResponse(product);
    }

    public void decrease(Long productId, int quantity) {
        Product product = productRepository.getBy(productId);
        product.deducted(quantity);

        productRepository.save(product);
    }
}
