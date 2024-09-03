package com.example.repository;

import com.example.model.Product;

import java.util.*;

public class InmemoryProductRepository implements ProductRepository {

    private final Map<Long, Product> products = new HashMap<>();

    @Override
    public Product save(Product product) {
        if (Objects.isNull(product.getId())) {
            int size = products.size();
            product = Product.builder()
                    .id(size == 0 ? 1 : Collections.max(products.keySet()) + 1)
                    .name(product.getName())
                    .price(product.getPrice())
                    .quantity(product.getQuantity())
                    .build();
        }

        products.put(product.getId(), product);
        return product;
    }

    @Override
    public List<Product> getAll() {
        return products.values().stream().toList();
    }

    @Override
    public Product getBy(Long productId) {
        return products.get(productId);
    }

    @Override
    public Product getLockBy(Long productId) {
        return products.get(productId);
    }

    @Override
    public boolean existsBy(String name) {
        return products.values().stream()
                .anyMatch(it -> it.getName().equals(name));
    }
}
