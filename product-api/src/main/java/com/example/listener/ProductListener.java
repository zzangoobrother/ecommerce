package com.example.listener;

import com.example.RabbitmqClient;
import com.example.application.ProductService;
import com.example.listener.dto.OrderEventDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProductListener {
    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${rabbitmq.routing.product.decrease.key}")
    private String routingProductDecreaseKey;

    private final ProductService productService;
    private final RabbitmqClient rabbitmqClient;

    public ProductListener(ProductService productService, RabbitmqClient rabbitmqClient) {
        this.productService = productService;
        this.rabbitmqClient = rabbitmqClient;
    }

    @RabbitListener(queues = "${rabbitmq.queue.product.decrease.name}")
    public void decrease(OrderEventDto orderEventDto) {
        try {
            if (orderEventDto.getCount() > 3) {
                log.info("상품 갯수 차감 3회 실패 Dead Letter Queue 이동");
                return;
            }

            productService.decrease(orderEventDto.getOrderCode());
        } catch (RuntimeException e) {
            orderEventDto.increasedCount();
            log.info("결제 실패 count : {}", orderEventDto.getCount());

            rabbitmqClient.send(exchangeName, routingProductDecreaseKey, orderEventDto);
        }
    }
}
