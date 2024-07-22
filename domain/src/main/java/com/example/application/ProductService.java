package com.example.application;

import com.example.application.dto.ProductDomainResponse;
import com.example.client.OrderClient;
import com.example.client.dto.response.OrderResponse;
import com.example.model.Product;
import com.example.repository.ProductRepository;
import com.example.repository.RedisCountRepository;
import com.example.repository.RedisRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final RedisCountRepository redisCountRepository;
    private final RedisRepository redisRepository;
    private final OrderClient orderClient;

    public ProductService(ProductRepository productRepository, RedisCountRepository redisCountRepository, RedisRepository redisRepository, OrderClient orderClient) {
        this.productRepository = productRepository;
        this.redisCountRepository = redisCountRepository;
        this.redisRepository = redisRepository;
        this.orderClient = orderClient;
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

//        redisCountRepository.add("product-" + saveProduct.getId(), (long) quantity);

        ObjectMapper mapper = new ObjectMapper();
        try {
            String s = mapper.writeValueAsString(ProductDomainResponse.toProductDomainResponse(product));
            redisRepository.add("product-" + saveProduct.getId(), s);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return saveProduct.getId();
    }

    public List<ProductDomainResponse> getAll() {
        return productRepository.getAll().stream()
                .map(ProductDomainResponse::toProductDomainResponse)
                .toList();
    }

    public ProductDomainResponse getBy(Long productId) {
        Product product = productRepository.getBy(productId);
        ProductDomainResponse productDomainResponse = ProductDomainResponse.toProductDomainResponse(product);

        ObjectMapper mapper = new ObjectMapper();
        try {
            redisRepository.add("product-" + product.getId(), mapper.writeValueAsString(productDomainResponse));
            return productDomainResponse;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void decrease(Long productId, int quantity) {
        Product product = productRepository.getBy(productId);
        product.deducted(quantity);

        productRepository.save(product);
    }

    public void decrease(String orderCode) {
        OrderResponse orderResponse = orderClient.getBy(orderCode);
        Product product = productRepository.getBy(orderResponse.productId());
        product.deducted(orderResponse.quantity());

        productRepository.save(product);
    }
}
