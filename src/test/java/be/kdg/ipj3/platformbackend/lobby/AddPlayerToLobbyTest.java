package be.kdg.ipj3.platformbackend.lobby;

import be.kdg.ipj3.platformbackend.lobby.application.LobbyService;
import be.kdg.ipj3.platformbackend.lobby.domain.Lobby;
import be.kdg.ipj3.platformbackend.lobby.domain.LobbyId;
import be.kdg.ipj3.platformbackend.lobby.infrastructure.DbLobbyRepository;
import be.kdg.ipj3.platformbackend.shared.domain.UserId;
import be.kdg.ipj3.platformbackend.user.domain.PlatformUser;
import be.kdg.ipj3.platformbackend.user.domain.repository.PlatformUserRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class AddPlayerToLobbyTest {
    @Mock
    DbLobbyRepository lobbyRepository;

    @Mock
    PlatformUserRepository platformUserRepository;

    @InjectMocks
    LobbyService lobbyService;

    @Nested
    class SuccessFlows {
        @Test
        void addPlayerToLobby_shouldAddUser_whenLobbyIsOpen() {
            // Arrange
            LobbyId lobbyId = new LobbyId(UUID.randomUUID());
            UserId userId = new UserId(UUID.randomUUID());
            Lobby lobby = new Lobby(lobbyId, null, new ArrayList<>(), LocalDateTime.now(), 5);
            PlatformUser user = new PlatformUser(userId, "u", "", new ArrayList<>(), "", "", 0.0, new ArrayList<>());
            Mockito.when(lobbyRepository.findLobbyById(lobbyId))
                    .thenReturn(Optional.of(lobby));
            Mockito.when(platformUserRepository.findUserById(userId))
                    .thenReturn(Optional.of(user));
            // Act
            Lobby result = lobbyService.addPlayerToLobby(lobbyId, userId);
            // Assert
            assertThat(result.getPlayers().size()).isEqualTo(1);
            Mockito.verify(lobbyRepository).save(lobby);
        }
    }

    @Nested
    class ErrorFlows {
        @Test
        void addPlayerToLobby_shouldThrowException_whenLobbyIsFull() {
            // Arrange
            LobbyId lobbyId = new LobbyId(UUID.randomUUID());
            UserId userId = new UserId(UUID.randomUUID());
            Lobby lobby = new Lobby(lobbyId, null, new ArrayList<>(), LocalDateTime.now(), 1);
            PlatformUser existingUser = new PlatformUser(new UserId(UUID.randomUUID()), "existing", "", null, "", "", 0.0, null);
            lobby.addPlayer(existingUser.getUserId());
            Mockito.when(lobbyRepository.findLobbyById(lobbyId))
                    .thenReturn(Optional.of(lobby));

            Mockito.when(platformUserRepository.findUserById(userId))
                    .thenReturn(Optional.of(new PlatformUser(userId, "new", "", null, "", "", 0.0, null)));
            // Act
            // Assert
            assertThatThrownBy(() -> lobbyService.addPlayerToLobby(lobbyId, userId))
                    .isInstanceOf(RuntimeException.class);
        }

        @Test
        void addPlayerToLobby_shouldThrowException_whenPlayerAlreadyInLobby() {
            // Arrange
            LobbyId lobbyId = new LobbyId(UUID.randomUUID());
            UserId userId = new UserId(UUID.randomUUID());
            Lobby lobby = new Lobby(lobbyId, null, new ArrayList<>(), LocalDateTime.now(), 5);
            PlatformUser user = new PlatformUser(userId, "u", "", new ArrayList<>(), "", "", 0.0, new ArrayList<>());
            lobby.addPlayer(user.getUserId());
            Mockito.when(lobbyRepository.findLobbyById(lobbyId))
                    .thenReturn(Optional.of(lobby));

            Mockito.when(platformUserRepository.findUserById(userId))
                    .thenReturn(Optional.of(user));
            // Act
            // Assert
            assertThatThrownBy(() -> lobbyService.addPlayerToLobby(lobbyId, userId))
                    .isInstanceOf(RuntimeException.class);
        }
    }
}
