package be.kdg.ipj3.platformbackend.infrastructure.game.rabbitMQ;

import be.kdg.ipj3.platformbackend.application.GameService;
import be.kdg.ipj3.platformbackend.infrastructure.game.rabbitMQ.messages.RegisterGameMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class gameMessageHandler {
    private final GameService gameService;

    public gameMessageHandler(GameService gameService) {
        this.gameService = gameService;
    }

    @RabbitListener(queues = "${spring.rabbitmq.fourteengames.register-game-queue}")
    void onRegisterGameRecieved(RegisterGameMessage message){
        log.info("Register game message received for Game: {}", message.gameDto().id());
        try {
            //TODO: add game registration
        } catch (IllegalStateException e) {
            log.error("Cannot register game {}: {}", message.gameDto().id(), e.getMessage());
        }
    }
}
