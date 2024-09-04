package com.example.application;

import com.example.application.dto.ProductDomainResponse;
import com.example.client.FakeOrderClient;
import com.example.client.OrderClient;
import com.example.model.Product;
import com.example.repository.InmemoryProductRepository;
import com.example.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class ProductServiceTest {

    private ProductService productService;
    private ProductRepository productRepository;

    private OrderClient orderClient;

    private final String name = "abcd";
    private final BigDecimal price = BigDecimal.valueOf(1000);
    private final int quantity = 10;

    @BeforeEach
    void setUp() {
        productRepository = new InmemoryProductRepository();
        orderClient = new FakeOrderClient();
        productService = new ProductService(productRepository, orderClient);
    }

    @Test
    void 상품_생성() {
        Long productId = productService.create(name, price, quantity);

        Product product = productRepository.getBy(productId);

        assertAll(
                () -> assertThat(product.getName()).isEqualTo(name),
                () -> assertThat(product.getPrice()).isEqualTo(price),
                () -> assertThat(product.getQuantity()).isEqualTo(quantity)
        );
    }

    @Test
    void 중복된_상품명_존재하면_에러() {
        productService.create(name, price, quantity);

        assertThatThrownBy(() -> productService.create(name, price, quantity))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("중복된 상품명을 가질 수 없습니다.");
    }

    @Test
    void 상품_갯수_차감() {
        Long productId = productService.create(name, price, quantity);
        productService.decrease(productId, 1);

        ProductDomainResponse productDomainResponse = productService.getBy(productId);

        assertAll(
                () -> assertThat(productDomainResponse.name()).isEqualTo(name),
                () -> assertThat(productDomainResponse.price()).isEqualTo(price),
                () -> assertThat(productDomainResponse.quantity()).isEqualTo(9)
        );
    }

    @Test
    void 주문코드_활용_상품_갯수_차감() {
        Long productId = productService.create(name, price, quantity);
        productService.decrease("20240903213111");

        ProductDomainResponse productDomainResponse = productService.getBy(productId);

        assertAll(
                () -> assertThat(productDomainResponse.name()).isEqualTo(name),
                () -> assertThat(productDomainResponse.price()).isEqualTo(price),
                () -> assertThat(productDomainResponse.quantity()).isEqualTo(9)
        );
    }
}
