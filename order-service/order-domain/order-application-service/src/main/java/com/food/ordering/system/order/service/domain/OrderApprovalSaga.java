package com.food.ordering.system.order.service.domain;

import com.food.ordering.system.domain.event.EmptyEvent;
import com.food.ordering.system.order.service.domain.dto.message.RestaurantApprovalResponse;
import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.event.OrderCanceledEvent;
import com.food.ordering.system.order.service.domain.ports.output.message.publisher.payment.OrderCanceledPaymentRequestMessagePublisher;
import com.food.ordering.system.saga.SagaStep;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderApprovalSaga implements SagaStep<RestaurantApprovalResponse, EmptyEvent, OrderCanceledEvent> {

    private final OrderDomainService orderDomainService;
    private final OrderSagaHelper orderSagaHelper;
    private final OrderCanceledPaymentRequestMessagePublisher orderCanceledPaymentRequestMessagePublisher;

    @Override
    @Transactional
    public EmptyEvent process(RestaurantApprovalResponse restaurantResponse) {
        log.info("Approving order with id: {}", restaurantResponse.getOrderId());
        Order order = orderSagaHelper.findOrder(restaurantResponse.getOrderId());
        orderDomainService.approveOrder(order);
        orderSagaHelper.saveOrder(order);
        log.info("Order with id: {} is approved", restaurantResponse.getOrderId());
        return EmptyEvent.INSTANCE;
    }

    @Override
    @Transactional
    public OrderCanceledEvent rollback(RestaurantApprovalResponse restaurantResponse) {
        log.info("Cancelling order with id: {}", restaurantResponse.getOrderId());
        Order order = orderSagaHelper.findOrder(restaurantResponse.getOrderId());
        OrderCanceledEvent domainEvent = orderDomainService.cancelOrderPayment(
                order, restaurantResponse.getFailureMessages(), orderCanceledPaymentRequestMessagePublisher);
        orderSagaHelper.saveOrder(order);
        log.info("Order with id: {} is cancelling", order.getId().getValue());
        return domainEvent;
    }
}
