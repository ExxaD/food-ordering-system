package com.food.ordering.system.order.service.domain.outbox.scheduler.approval;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.ordering.system.domain.valueobject.OrderStatus;
import com.food.ordering.system.order.service.domain.exception.OrderDomainException;
import com.food.ordering.system.order.service.domain.outbox.model.approval.OrderApprovalEventPayload;
import com.food.ordering.system.order.service.domain.outbox.model.approval.OrderApprovalOutboxMessage;
import com.food.ordering.system.order.service.domain.ports.output.repository.ApprovalOutboxRepository;
import com.food.ordering.system.outbox.OutboxStatus;
import com.food.ordering.system.saga.SagaStatus;
import com.food.ordering.system.saga.order.SagaConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApprovalOutboxHelper {

    private final ApprovalOutboxRepository approvalOutboxRepository;
    private final ObjectMapper objectMapper;

    @Transactional(readOnly = true)
    public Optional<List<OrderApprovalOutboxMessage>> getApprovalOutboxMessageByOutboxStatusAndSagaStatus(
            OutboxStatus outboxStatus, SagaStatus... sagaStatuses) {
        return approvalOutboxRepository.findByTypeAndOutboxStatusAndSagaStatus(SagaConstants.ORDER_SAGA_NAME,
                outboxStatus, sagaStatuses);
    }

    @Transactional(readOnly = true)
    public Optional<OrderApprovalOutboxMessage> getApprovalOutboxMessageBySagaIdAndSagaStatus(
            UUID sagaId, SagaStatus... sagaStatuses) {

        return approvalOutboxRepository.findByTypeAndSagaIdAndSagaStatus(SagaConstants.ORDER_SAGA_NAME,
                sagaId, sagaStatuses);
    }

    @Transactional
    public void save(OrderApprovalOutboxMessage orderApprovalOutboxMessage) {
        OrderApprovalOutboxMessage response = approvalOutboxRepository.save(orderApprovalOutboxMessage);
        if (response == null) {
            log.error("Could not save OrderApprovalOutboxMessage with outbox id: {}",
                    orderApprovalOutboxMessage.getId());
            throw new OrderDomainException("Could not save OrderApprovalOutboxMessage with outbox id: "
                    + orderApprovalOutboxMessage.getId());
        }
        log.info("OrderApprovalOutboxMessage saved with outbox id: {}", orderApprovalOutboxMessage.getId());
    }

    @Transactional
    public void saveApprovalOutboxMessage(OrderApprovalEventPayload orderApprovalEventPayload,
                                          OrderStatus orderStatus,
                                          SagaStatus sagaStatus,
                                          OutboxStatus outboxStatus,
                                          UUID sagaId) {

        save(OrderApprovalOutboxMessage.builder()
                .id(UUID.randomUUID())
                .sagaId(sagaId)
                .createdAt(orderApprovalEventPayload.getCreatedAt())
                .type(SagaConstants.ORDER_SAGA_NAME)
                .payload(createPayload(orderApprovalEventPayload))
                .orderStatus(orderStatus)
                .sagaStatus(sagaStatus)
                .outboxStatus(outboxStatus)
                .build());
    }

    @Transactional
    public void deleteApprovalOutboxMessageByOutboxStatusAndSagaStatus(
            OutboxStatus outboxStatus, SagaStatus... sagaStatuses) {
        approvalOutboxRepository.deleteByTypeAndOutboxStatusAndSagaStatus(SagaConstants.ORDER_SAGA_NAME,
                outboxStatus, sagaStatuses);
    }

    private String createPayload(OrderApprovalEventPayload orderApprovalEventPayload) {
        try {
            return objectMapper.writeValueAsString(orderApprovalEventPayload);
        } catch (JsonProcessingException e) {
            log.error("Could not create OrderApprovalEventPayload string for order id: {}",
                    orderApprovalEventPayload.getOrderId());
            throw new OrderDomainException("Could not create OrderApprovalEventPayload string for order id: "
                    + orderApprovalEventPayload.getOrderId());
        }
    }
}
