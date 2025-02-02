package com.food.ordering.system.order.service.domain.event;

import com.food.ordering.system.domain.event.publisher.DomainEventPublisher;
import com.food.ordering.system.order.service.domain.entity.Order;

import java.time.ZonedDateTime;

public class OrderCanceledEvent extends OrderEvent {

    private final DomainEventPublisher<OrderCanceledEvent> orderCanceledEventDomainEventPublisher;

    public OrderCanceledEvent(Order order,
                              ZonedDateTime createdAt,
                              DomainEventPublisher<OrderCanceledEvent> orderCanceledEventDomainEventPublisher) {
        super(order, createdAt);
        this.orderCanceledEventDomainEventPublisher = orderCanceledEventDomainEventPublisher;
    }

    @Override
    public void fire() {
        orderCanceledEventDomainEventPublisher.publish(this);
    }
}
