package com.example.application.listener;

import com.example.application.event.OrderEventDto;
import com.example.client.QueueClient;
import com.example.client.dto.request.QueueRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class QueueEventListener {

    private final QueueClient queueClient;

    public QueueEventListener(QueueClient queueClient) {
        this.queueClient = queueClient;
    }

    @TransactionalEventListener
    public void queueMessage(OrderEventDto orderEventDto) {
        queueClient.complete(new QueueRequest(orderEventDto.token()));
    }
}
