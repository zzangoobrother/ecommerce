package com.example.repository;

import com.example.model.Product;

import java.util.List;

public interface ProductRepository {
    Product save(Product product);

    List<Product> getAll();

    Product getBy(Long productId);

    Product getLockBy(Long productId);

    boolean existsBy(String name);
}
