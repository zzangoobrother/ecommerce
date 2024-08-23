package com.example.listener;

import com.example.application.ProductService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ProductListener {

    private final ProductService productService;

    public ProductListener(ProductService productService) {
        this.productService = productService;
    }

    @RabbitListener(queues = "${rabbitmq.queue.product.decrease.name}")
    public void decrease(String orderCode) {
        productService.decrease(orderCode);
    }
}
