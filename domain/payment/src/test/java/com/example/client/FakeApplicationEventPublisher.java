package com.example.client;

import org.springframework.context.ApplicationEventPublisher;

public class FakeApplicationEventPublisher implements ApplicationEventPublisher  {
    @Override
    public void publishEvent(Object event) {

    }
}
