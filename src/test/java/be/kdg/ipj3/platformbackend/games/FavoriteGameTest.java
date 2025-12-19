package be.kdg.ipj3.platformbackend.games;

import be.kdg.ipj3.platformbackend.shared.domain.exception.NotFoundException;
import be.kdg.ipj3.platformbackend.game.domain.Game;
import be.kdg.ipj3.platformbackend.game.domain.GameId;
import be.kdg.ipj3.platformbackend.game.domain.Genre;
import be.kdg.ipj3.platformbackend.game.domain.repository.GameRepository;
import be.kdg.ipj3.platformbackend.shared.domain.UserId;
import be.kdg.ipj3.platformbackend.user.application.UserService;
import be.kdg.ipj3.platformbackend.user.domain.OwnedCopy;
import be.kdg.ipj3.platformbackend.user.domain.OwnedCopyId;
import be.kdg.ipj3.platformbackend.user.domain.PlatformUser;
import be.kdg.ipj3.platformbackend.user.domain.repository.PlatformUserRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class FavoriteGameTest {

    @Mock
    GameRepository gameRepository;
    @Mock
    PlatformUserRepository platformUserRepository;

    @InjectMocks
    UserService userService;

    @Nested
    class SuccessFlows {
        @Test
        void addFavoriteExistingGame_Should_return_Game() {
            // Arrange
            ArgumentCaptor<OwnedCopy> favoriteGameCaptor = ArgumentCaptor.forClass(OwnedCopy.class);
            UUID selectedGameId = UUID.randomUUID();
            UUID currentUserId = UUID.randomUUID();
            GameId gameId = new GameId(selectedGameId);
            UserId userId = new UserId(currentUserId);
            PlatformUser returnValue = new PlatformUser(userId, "","",new ArrayList<>(),"","",0.0,new ArrayList<>());
            Genre puzzle = new Genre("Puzzle","Genre where you solve puzzles");
            Game game1 = new Game(new GameId(),"Tic Tac Toe", "Game where you...", 20, "testimg.png", "testicon.png", "localhost:8080", puzzle, new ArrayList<>());
            Mockito.when(platformUserRepository.save(Mockito.any(PlatformUser.class)))
                    .thenReturn(returnValue);
            Mockito.when(gameRepository.findById(selectedGameId))
                    .thenReturn(Optional.of(game1));

            // Act
            Game result = userService.addFavoriteGame(gameId, userId);
            //Assert
            assertThat(result).isNotNull();
            assertThat(result.getName()).isEqualTo("Tic Tac Toe");
            Mockito.verify(platformUserRepository).save(favoriteGameCaptor.capture());
            FavoriteGame captured = favoriteGameCaptor.getValue();
            assertThat(captured.getFavoriteGameId().getUserId()).isEqualTo(currentUserId);
            assertThat(captured.getFavoriteGameId().getGameId()).isEqualTo(selectedGameId);
            Mockito.verify(gameRepository, times(2)).findById(selectedGameId);
            Mockito.verifyNoMoreInteractions(gameRepository);
            Mockito.verifyNoMoreInteractions(platformUserRepository);
        }

        @Test
        void removeExistingGameFavorite_Should_not_throw_error() {
            // Arrange
            ArgumentCaptor<FavoriteGame> favoriteGameCaptor = ArgumentCaptor.forClass(FavoriteGame.class);
            UUID selectedGameId = UUID.randomUUID();
            UUID currentUserId = UUID.randomUUID();
            GameId gameId = new GameId(selectedGameId);
            UserId userId = new UserId(currentUserId);
            Genre puzzle = new Genre("Puzzle","Genre where you solve puzzles");
            Game game1 = new Game(new GameId(),"Tic Tac Toe", "Game where you...", 20, "testimg.png", "testicon.png", "localhost:8080", puzzle, new ArrayList<>());

            Mockito.when(gameRepository.findById(selectedGameId))
                    .thenReturn(Optional.of(game1));

            // Act
            userService.removeFavoriteGame(gameId, userId);

            //assert
            Mockito.verify(gameRepository, times(1)).findById(selectedGameId);
            Mockito.verify(platformUserRepository).remove(favoriteGameCaptor.capture());
        }
    }

    @Nested
    class ErrorFlows{
        @Test
        void addFavoriteNonExistantGame_shouldThrow_404() {
            // Arrange
            String uuid = "00000000-0000-0000-0000-000000000000";
            UUID userId  = UUID.randomUUID();
            GameId invalidGameId = new GameId(UUID.fromString(uuid));

            Mockito.when(gameRepository.findById(invalidGameId.id()))
                    .thenReturn(Optional.empty());

            // Act & Assert
            assertThatThrownBy(() -> userService.addFavoriteGame(invalidGameId,new UserId(userId)))
                    .isInstanceOf(NotFoundException.class);

            Mockito.verify(gameRepository, times(1)).findById(invalidGameId.id());
            Mockito.verifyNoInteractions(platformUserRepository);
            Mockito.verifyNoMoreInteractions(gameRepository);
        }

        @Test
        void removeNonExistingGameFavorite_Should_throw_404() {
            String uuid = "00000000-0000-0000-0000-000000000000";
            UUID userId  = UUID.randomUUID();
            GameId invalidGameId = new GameId(UUID.fromString(uuid));

            Mockito.when(gameRepository.findById(invalidGameId.id()))
                    .thenReturn(Optional.empty());

            // Act & Assert
            assertThatThrownBy(() -> userService.removeFavoriteGame(invalidGameId,new UserId(userId)))
                    .isInstanceOf(NotFoundException.class);

            Mockito.verify(gameRepository, times(1)).findById(invalidGameId.id());
            Mockito.verifyNoInteractions(platformUserRepository);
            Mockito.verifyNoMoreInteractions(gameRepository);
        }
    }
}
