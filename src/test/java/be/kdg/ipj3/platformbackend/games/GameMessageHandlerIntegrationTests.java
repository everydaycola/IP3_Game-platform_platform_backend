package be.kdg.ipj3.platformbackend.games;

import be.kdg.ipj3.platformbackend.game.api.dtos.FullGameDto;
import be.kdg.ipj3.platformbackend.game.domain.Game;
import be.kdg.ipj3.platformbackend.game.domain.GameId;
import be.kdg.ipj3.platformbackend.game.domain.Genre;
import be.kdg.ipj3.platformbackend.game.domain.repository.GameRepository;
import be.kdg.ipj3.platformbackend.game.infrastructure.rabbitMQ.messages.RegisterGameMessage;
import be.kdg.ipj3.platformbackend.shared.api.UrlChecker;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Duration;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@SpringBootTest
@Testcontainers
public class GameMessageHandlerIntegrationTests {

    @Container
    static RabbitMQContainer rabbit = new RabbitMQContainer("rabbitmq:3.12-management")
            .withExposedPorts(5672);

    @DynamicPropertySource
    static void configureRabbit(DynamicPropertyRegistry registry) {
        registry.add("spring.rabbitmq.host", rabbit::getHost);
        registry.add("spring.rabbitmq.port", rabbit::getAmqpPort);
        registry.add("spring.rabbitmq.username", rabbit::getAdminUsername);
        registry.add("spring.rabbitmq.password", rabbit::getAdminPassword);
        registry.add("spring.rabbitmq.fourteengames.register-game-queue", () -> "register-game-test-queue");
    }

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @MockitoBean
    private UrlChecker urlChecker;

    @MockitoBean
    private GameRepository gameRepository;

    @Nested
    class RegisterGameFlows {
        @Test
        void whenMessageSent_itIsConsumed() {
            // Arrange
            Genre puzzle = new Genre("Puzzle","Genre where you solve puzzles");
            Game game1 = new Game(new GameId(),"Tic Tac Toe", "Game where you...", 20,
                    "testimg.png", "testicon.png", "http://localhost:8080", puzzle);

            RegisterGameMessage message = new RegisterGameMessage(FullGameDto.from(game1));

            // Mock repository & URL checker
            Mockito.when(gameRepository.findGenre(puzzle.getName())).thenReturn(Optional.of(puzzle));
            Mockito.when(urlChecker.isUrlReachable(game1.getUrl())).thenReturn(true);

            // Act
            rabbitTemplate.convertAndSend("register-game-test-queue", message);

            // Assert
            Awaitility.await()
                    .atMost(Duration.ofSeconds(5))
                    .untilAsserted(() -> {
                        ArgumentCaptor<Game> captor = ArgumentCaptor.forClass(Game.class);
                        verify(gameRepository).save(captor.capture());

                        Game captured = captor.getValue();
                        assertThat(captured.getId().id()).isEqualTo(game1.getId().id());
                        assertThat(captured.getName()).isEqualTo(game1.getName());
                        assertThat(captured.getUrl()).isEqualTo(game1.getUrl());
                    });
        }

        @Test
        void whenMalformedMessageIsSent_listenerDoesNotCrash() {
            // Arrange
            String badJson = "{ \"invalid\": \"data\" }";

            // Act
            rabbitTemplate.convertAndSend("register-game-test-queue", badJson);

            // Assert
            Awaitility.await()
                    .atMost(Duration.ofSeconds(5))
                    .untilAsserted(() -> verify(gameRepository, Mockito.never()).save(Mockito.any()));
        }
    }
}
