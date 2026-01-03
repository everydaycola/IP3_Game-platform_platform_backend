package be.kdg.ipj3.platformbackend.lobby;

import be.kdg.ipj3.platformbackend.game.domain.Game;
import be.kdg.ipj3.platformbackend.game.domain.GameId;
import be.kdg.ipj3.platformbackend.game.domain.Genre;
import be.kdg.ipj3.platformbackend.game.domain.repository.GameRepository;
import be.kdg.ipj3.platformbackend.lobby.application.LobbyService;
import be.kdg.ipj3.platformbackend.lobby.domain.Lobby;
import be.kdg.ipj3.platformbackend.lobby.infrastructure.DbLobbyRepository;
import be.kdg.ipj3.platformbackend.shared.domain.UserId;
import be.kdg.ipj3.platformbackend.user.domain.PlatformUser;
import be.kdg.ipj3.platformbackend.user.domain.repository.PlatformUserRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateLobbyTest {
    @Mock
    DbLobbyRepository lobbyRepository;
    @Mock
    GameRepository gameRepository;
    @Mock
    PlatformUserRepository platformUserRepository;

    @InjectMocks
    LobbyService lobbyService;

    @Nested
    class SuccessFlows {
        @Test
        void createNewLobby_shouldReturnLobby_whenDataIsValid() {
            // Arrange
            UserId userId = new UserId(UUID.randomUUID());
            GameId gameId = new GameId(UUID.randomUUID());
            int maxPlayerCount = 4;
            Game game = new Game(new GameId(UUID.randomUUID()),"myGame",2,"MyDescription",5.00,"","","localhost:8080",new Genre("strategy",""),new ArrayList<>(),"localhost:8080/start/ai","localhost:8080/start", new HashMap<>());
            Game mockGame = new Game(gameId, "Test Game", 2,"Desc", 10.0, "img", "icon", "url", null, new ArrayList<>(), "url/start/ai", "/url/start", new HashMap<>());
            PlatformUser mockUser = new PlatformUser(userId, "user1", "email", new ArrayList<>(), "", "", 100.0, new ArrayList<>());
            Lobby expectedLobby = new Lobby(null, mockGame.getId(),null, new ArrayList<>(), null, game.getMaxPlayerCount());
            when(gameRepository.findById(gameId.id())).thenReturn(Optional.of(mockGame));
            when(platformUserRepository.findUserById(userId)).thenReturn(Optional.of(mockUser));
            when(lobbyRepository.createNewLobby(any(Lobby.class), any(Game.class))).thenReturn(expectedLobby);
            // Act
            Lobby result = lobbyService.createNewLobby(userId, maxPlayerCount, gameId);
            // Assert
            assertThat(result).isNotNull();
            assertThat(result.getMaxPlayerCount()).isEqualTo(maxPlayerCount);
            verify(lobbyRepository).createNewLobby(any(Lobby.class), any(Game.class));
        }
    }

    @Nested
    class ErrorFlows {
        @Test
        void createNewLobby_shouldThrowException_whenGameNotFound() {
            // Arrange
            UserId userId = new UserId(UUID.randomUUID());
            GameId gameId = new GameId(UUID.randomUUID());
            when(gameRepository.findById(gameId.id())).thenReturn(Optional.empty());
            // Act
            // Assert
            assertThatThrownBy(() -> lobbyService.createNewLobby(userId, 4, gameId))
                    .isInstanceOf(RuntimeException.class);
        }

        @Test
        void createNewLobby_shouldThrowException_whenUserNotFound() {
            // Arrange
            UserId userId = new UserId(UUID.randomUUID());
            GameId gameId = new GameId(UUID.randomUUID());
            Game mockGame = new Game(gameId, "Test Game", 2,"Desc", 10.0, "img", "icon", "url", null, new ArrayList<>(), "url/start/ai", "/url/start", new HashMap<>());
            when(gameRepository.findById(gameId.id())).thenReturn(Optional.of(mockGame));
            when(platformUserRepository.findUserById(userId)).thenReturn(Optional.empty());
            // Act
            // Assert
            assertThatThrownBy(() -> lobbyService.createNewLobby(userId, 4, gameId))
                    .isInstanceOf(RuntimeException.class);
        }
    }
}
