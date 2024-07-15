package com.example.listener;

import com.example.application.ProductService;
import com.example.listener.dto.ProductDecreaseRequest;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ProductListener {

    private final ProductService productService;

    public ProductListener(ProductService productService) {
        this.productService = productService;
    }

    @RabbitListener(queues = "${rabbitmq.queue.product.decrease.name}")
    public void decrease(ProductDecreaseRequest request) {
        productService.decrease(request.productId(), request.quantity());
    }
}
