package com.example.application;

import com.example.application.dto.ProductDomainResponse;
import com.example.client.OrderClient;
import com.example.client.dto.response.OrderResponse;
import com.example.model.Product;
import com.example.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final OrderClient orderClient;

    public ProductService(ProductRepository productRepository, OrderClient orderClient) {
        this.productRepository = productRepository;
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

    @Transactional
    public void decrease(String orderCode) {
        List<OrderResponse> orderResponse = orderClient.getBy(orderCode);
        List<Long> productIds = orderResponse.stream()
                .map(OrderResponse::productId)
                .toList();
        List<Product> products = productRepository.getAllBy(productIds);

        Map<Long, OrderResponse> orderResponseMap = orderResponse.stream()
                .collect(Collectors.toMap(OrderResponse::productId, Function.identity()));
        products.forEach(it -> {
            it.deducted(orderResponseMap.get(it.getId()).quantity());
        });

        productRepository.saveAll(products);
    }
}
