package be.kdg.ipj3.platformbackend.analytics.infrastructure;

import be.kdg.ipj3.platformbackend.analytics.events.*;
import be.kdg.ipj3.platformbackend.config.rabbitMQ.RabbitMQProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AnalyticsEventPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQProperties properties;

    public AnalyticsEventPublisher(RabbitTemplate rabbitTemplate, RabbitMQProperties properties) {
        this.rabbitTemplate = rabbitTemplate;
        this.properties = properties;
    }

    public void publishGameStartedEvent(GameStartedEvent event) {
        publishEvent(event, "game.session.started");
    }

    public void publishGameEndedEvent(GameEndedEvent event) {
        publishEvent(event, "game.session.ended");
    }

    public void publishUserLoggedInEvent(UserLoggedInEvent event) {
        publishEvent(event, "user.session.logged_in");
    }

    public void publishUserRegisteredEvent(UserRegisteredEvent event) {
        publishEvent(event, "user.account.registered");
    }

    public void publishFriendAddedEvent(FriendAddedEvent event) {
        publishEvent(event, "user.social.friend_added");
    }

    public void publishGamePageVisitEvent(GamePageVisitEvent event) {
        publishEvent(event, "platform.page.visited");
    }

    public void publishPurchaseMadeEvent(PurchaseMadeEvent event) {
        publishEvent(event, "platform.transaction.purchase");
    }

    /**
     * Generic method to publish any event to RabbitMQ
     * Includes error handling to prevent service disruption if RabbitMQ is unavailable
     */
    private void publishEvent(BaseEvent event, String routingKey) {
        try {
            rabbitTemplate.convertAndSend(
                properties.getAnalyticsExchange(),
                routingKey,
                event
            );
            log.info("Published event: {} to routing key: {}", event.getEventType(), routingKey);
        } catch (AmqpException e) {
            log.warn("Failed to publish event: {} - RabbitMQ connection error: {}",
                    event.getEventType(), e.getMessage());
            // Don't throw exception - allow main business logic to continue
        } catch (Exception e) {
            log.error("Unexpected error publishing event: {} - {}",
                    event.getEventType(), e.getMessage(), e);
        }
    }
}

