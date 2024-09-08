package com.example.application.listener;

import com.example.application.event.OrderEventDto;
import com.example.model.OrderEvent;
import com.example.model.OrderEventStatus;
import com.example.repository.OrderEventRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class OrderEventListener {
    private final OrderEventRepository orderEventRepository;

    public OrderEventListener(OrderEventRepository orderEventRepository) {
        this.orderEventRepository = orderEventRepository;
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void orderMessage(OrderEventDto orderEventDto) {
        orderEventRepository.save(OrderEvent.builder().ordersCode(orderEventDto.orderCode()).status(OrderEventStatus.INIT).build());
    }
}
