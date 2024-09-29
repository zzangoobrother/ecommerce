package com.example.repository;

import com.example.entity.ProductEntity;
import com.example.model.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
class ProductEntityRepository implements ProductRepository {

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

    @Override
    public Product getBy(Long productId) {
        return repository.findById(productId).orElseThrow(
                () -> new IllegalArgumentException("해당 상품을 찾을 수 없습니다.")
        ).toProduct();
    }

    @Override
    public Product getLockBy(Long productId) {
        return repository.findLockById(productId).orElseThrow(
                () -> new IllegalArgumentException("해당 상품을 찾을 수 없습니다.")
        ).toProduct();
    }

    @Override
    public boolean existsBy(String name) {
        return repository.existsByName(name);
    }

    @Override
    public List<Product> getAllBy(List<Long> productIds) {
        return repository.findAllById(productIds).stream()
                .map(ProductEntity::toProduct)
                .toList();
    }

    @Override
    public List<Product> saveAll(List<Product> products) {
        List<ProductEntity> result = products.stream()
                .map(ProductEntity::toProductEntity)
                .toList();
        return repository.saveAll(result).stream()
                .map(ProductEntity::toProduct)
                .toList();
    }
}
