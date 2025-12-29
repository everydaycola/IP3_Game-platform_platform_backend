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
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class LeaveLobbyTest {
    @Mock
    DbLobbyRepository lobbyRepository;

    @Mock
    PlatformUserRepository platformUserRepository;

    @InjectMocks
    LobbyService lobbyService;

    @Nested
    class SuccessFlows {
        @Test
        void leaveLobby_shouldRemovePlayerAndSave_whenOtherPlayersRemain() {
            // Arrange
            LobbyId lobbyId = new LobbyId(UUID.randomUUID());
            UserId userId1 = new UserId(UUID.randomUUID());
            UserId userId2 = new UserId(UUID.randomUUID());
            Lobby lobby = new Lobby(lobbyId, null, new ArrayList<>(), LocalDateTime.now(), 5);
            PlatformUser user1 = new PlatformUser(userId1, "u1", "", new ArrayList<>(), "", "", 0.0, new ArrayList<>());
            PlatformUser user2 = new PlatformUser(userId2, "u2", "", new ArrayList<>(), "", "", 0.0, new ArrayList<>());
            lobby.addPlayer(user1);
            lobby.addPlayer(user2);
            Mockito.when(lobbyRepository.findLobbyById(lobbyId))
                    .thenReturn(Optional.of(lobby));

            Mockito.when(platformUserRepository.findUserById(userId1))
                    .thenReturn(Optional.of(user1));
            // Act
            lobbyService.leaveLobby(userId1, lobbyId);
            // Assert
            assertThat(lobby.getPlayers().size()).isEqualTo(1);
            Mockito.verify(lobbyRepository).save(lobby);
            Mockito.verify(lobbyRepository, Mockito.never()).remove(any());
        }

        @Test
        void leaveLobby_shouldRemoveLobby_whenLastPlayerLeaves() {
            // Arrange
            LobbyId lobbyId = new LobbyId(UUID.randomUUID());
            UserId userId = new UserId(UUID.randomUUID());
            Lobby lobby = new Lobby(lobbyId, null, new ArrayList<>(), LocalDateTime.now(), 5);
            PlatformUser user = new PlatformUser(userId, "u1", "", new ArrayList<>(), "", "", 0.0, new ArrayList<>());
            lobby.addPlayer(user);
            Mockito.when(lobbyRepository.findLobbyById(lobbyId))
                    .thenReturn(Optional.of(lobby));
            Mockito.when(platformUserRepository.findUserById(userId))
                    .thenReturn(Optional.of(user));
            // Act
            lobbyService.leaveLobby(userId, lobbyId);
            // Assert
            assertThat(lobby.getPlayers().isEmpty()).isTrue();
            Mockito.verify(lobbyRepository).remove(lobby);
            Mockito.verify(lobbyRepository, Mockito.never()).save(lobby);
        }
    }

    @Nested
    class ErrorFlows {
        @Test
        void leaveLobby_shouldThrowException_whenUserNotInLobby() {
            // Arrange
            LobbyId lobbyId = new LobbyId(UUID.randomUUID());
            UserId userId = new UserId(UUID.randomUUID());
            UserId otherId = new UserId(UUID.randomUUID());
            Lobby lobby = new Lobby(lobbyId, null, new ArrayList<>(), LocalDateTime.now(), 5);
            PlatformUser otherUser = new PlatformUser(otherId, "u2", "", new ArrayList<>(), "", "", 0.0, new ArrayList<>());
            lobby.addPlayer(otherUser);
            Mockito.when(lobbyRepository.findLobbyById(lobbyId))
                    .thenReturn(Optional.of(lobby));
            PlatformUser requestingUser = new PlatformUser(userId, "u1", "", null, "", "", 0.0, null);
            Mockito.when(platformUserRepository.findUserById(userId))
                    .thenReturn(Optional.of(requestingUser));
            // Act
            // Assert
            assertThatThrownBy(() -> lobbyService.leaveLobby(userId, lobbyId))
                    .isInstanceOf(RuntimeException.class);
        }
    }
}
