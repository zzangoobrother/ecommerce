package com.example.schedule;

import com.example.RabbitmqClient;
import com.example.model.OrderEvent;
import com.example.model.OrderEventStatus;
import com.example.repository.OrderEventRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
public class OrderEventSchedule {
    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${rabbitmq.routing.payment.key}")
    private String routingPaymentKey;

    private final OrderEventRepository orderEventRepository;
    private final RabbitmqClient rabbitmqClient;

    public OrderEventSchedule(OrderEventRepository orderEventRepository, RabbitmqClient rabbitmqClient) {
        this.orderEventRepository = orderEventRepository;
        this.rabbitmqClient = rabbitmqClient;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Scheduled(fixedRate = 500)
    public void run() {
        List<OrderEvent> orderEvents = orderEventRepository.getAllBy(OrderEventStatus.INIT);

        if (CollectionUtils.isEmpty(orderEvents)) {
            return;
        }


        for (OrderEvent orderEvent : orderEvents) {
            rabbitmqClient.send(exchangeName, routingPaymentKey, new OrderEventDto(orderEvent.getOrdersCode(), 0));
            orderEvent.success();
        }

        orderEventRepository.saveAll(orderEvents);
    }
}
