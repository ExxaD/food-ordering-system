package com.food.ordering.system.order.service.domain.outbox.scheduler.approval;

import com.food.ordering.system.order.service.domain.outbox.model.approval.OrderApprovalOutboxMessage;
import com.food.ordering.system.order.service.domain.ports.output.message.publisher.restaurantapproval.RestaurantApprovalRequestMessagePublisher;
import com.food.ordering.system.outbox.OutboxScheduler;
import com.food.ordering.system.outbox.OutboxStatus;
import com.food.ordering.system.saga.SagaStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class RestaurantApprovalOutboxScheduler implements OutboxScheduler {

    private final ApprovalOutboxHelper approvalOutboxHelper;
    private final RestaurantApprovalRequestMessagePublisher restaurantApprovalRequestMessagePublisher;

    @Override
    @Transactional
    @Scheduled(fixedDelayString = "${order-service.outbox-scheduler-fixed-rate}",
            initialDelayString = "${order-service.outbox-scheduler-initial-delay}")
    public void processOutboxMessage() {
        Optional<List<OrderApprovalOutboxMessage>> outboxMessageResponse =
                approvalOutboxHelper.getApprovalOutboxMessageByOutboxStatusAndSagaStatus(
                        OutboxStatus.STARTED,
                        SagaStatus.PROCESSING);

        if (outboxMessageResponse.isPresent() && !outboxMessageResponse.get().isEmpty()) {
            List<OrderApprovalOutboxMessage> outboxMessages = outboxMessageResponse.get();
            log.info("Received {} OrderApprovalOutboxMessage with ids: {}, sending to message bus!",
                    outboxMessages.size(),
                    outboxMessages.stream()
                            .map(m -> m.getId().toString())
                            .collect(Collectors.joining(",")));
            outboxMessages.forEach(m -> restaurantApprovalRequestMessagePublisher.publish(m, this::updateOutboxStatus));
            log.info("{} OrderApprovalOutboxMessage sent to message bus!", outboxMessages.size());
        }
    }

    private void updateOutboxStatus(OrderApprovalOutboxMessage orderApprovalOutboxMessage, OutboxStatus outboxStatus) {
        orderApprovalOutboxMessage.setOutboxStatus(outboxStatus);
        approvalOutboxHelper.save(orderApprovalOutboxMessage);
        log.info("OrderApprovalOutboxMessage is updated with outbox status: {}", outboxStatus.name());
    }
}
