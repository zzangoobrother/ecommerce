package com.example.repository;

import com.example.entity.ProductEntity;
import com.example.model.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductEntityRepository implements ProductRepository {

    private final ProductJpaRepository repository;

    public ProductEntityRepository(ProductJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Product save(Product product) {
        return repository.save(ProductEntity.toProductEntity(product)).toProduct();
    }

    @Override
    public List<Product> getAll() {
        return repository.findAll().stream()
                .map(ProductEntity::toProduct)
                .toList();
    }
}
