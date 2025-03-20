package com.food.ordering.system.order.service.dataaccess.outbox.payment.adapter;

import com.food.ordering.system.order.service.dataaccess.outbox.payment.mapper.PaymentOutboxDataAccessMapper;
import com.food.ordering.system.order.service.dataaccess.outbox.payment.repository.PaymentOutboxJpaRepository;
import com.food.ordering.system.order.service.domain.outbox.model.payment.OrderPaymentOutboxMessage;
import com.food.ordering.system.order.service.domain.ports.output.repository.PaymentOutboxRepository;
import com.food.ordering.system.outbox.OutboxStatus;
import com.food.ordering.system.saga.SagaStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PaymentOutboxRepositoryImpl implements PaymentOutboxRepository {

    private final PaymentOutboxJpaRepository paymentOutboxJpaRepository;
    private final PaymentOutboxDataAccessMapper paymentOutboxDataAccessMapper;

    @Override
    public OrderPaymentOutboxMessage save(OrderPaymentOutboxMessage orderPaymentOutboxMessage) {
        return paymentOutboxDataAccessMapper
                .paymentOutboxEntityToOrderPaymentOutboxMessage(paymentOutboxJpaRepository
                        .save(paymentOutboxDataAccessMapper
                                .orderPaymentOutboxMessageToPaymentOutboxEntity(orderPaymentOutboxMessage)));
    }

    @Override
    public Optional<List<OrderPaymentOutboxMessage>> findByTypeAndOutboxStatusAndSagaStatus(String sagaType,
                                                                                            OutboxStatus outboxStatus,
                                                                                            SagaStatus... sagaStatuses) {
        return Optional.of(paymentOutboxJpaRepository.findByTypeAndOutboxStatusAndSagaStatusIn(
                        sagaType, outboxStatus, Arrays.asList(sagaStatuses))
                .orElse(Collections.emptyList())
                .stream()
                .map(paymentOutboxDataAccessMapper::paymentOutboxEntityToOrderPaymentOutboxMessage)
                .collect(Collectors.toList()));
    }

    @Override
    public Optional<OrderPaymentOutboxMessage> findByTypeAndSagaIdAndSagaStatus(String sagaType,
                                                                                UUID sagaId,
                                                                                SagaStatus... sagaStatuses) {
        return paymentOutboxJpaRepository
                .findByTypeAndSagaIdAndSagaStatusIn(sagaType, sagaId, Arrays.asList(sagaStatuses))
                .map(paymentOutboxDataAccessMapper::paymentOutboxEntityToOrderPaymentOutboxMessage);
    }

    @Override
    public void deleteByTypeAndOutboxStatusAndSagaStatus(String sagaType,
                                                         OutboxStatus outboxStatus,
                                                         SagaStatus... sagaStatuses) {
        paymentOutboxJpaRepository
                .deleteByTypeAndOutboxStatusAndSagaStatusIn(sagaType, outboxStatus, Arrays.asList(sagaStatuses));
    }
}
