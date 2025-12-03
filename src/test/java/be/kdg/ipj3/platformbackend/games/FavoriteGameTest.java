package be.kdg.ipj3.platformbackend.games;

import be.kdg.ipj3.platformbackend.application.GameService;
import be.kdg.ipj3.platformbackend.domain.exception.NotFoundException;
import be.kdg.ipj3.platformbackend.domain.game.FavoriteGame;
import be.kdg.ipj3.platformbackend.domain.game.Game;
import be.kdg.ipj3.platformbackend.domain.game.GameId;
import be.kdg.ipj3.platformbackend.domain.game.Genre;
import be.kdg.ipj3.platformbackend.domain.repository.FavoriteGameRepository;
import be.kdg.ipj3.platformbackend.domain.repository.GameRepository;
import be.kdg.ipj3.platformbackend.domain.user.UserId;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
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
    FavoriteGameRepository favoriteGameRepository;

    @InjectMocks
    GameService gameService;

    @Nested
    class SuccesFlows {
        @Test
        void addFavoriteExistingGame_Should_return_Game() {
            // Arrange
            ArgumentCaptor<FavoriteGame> favoriteGameCaptor = ArgumentCaptor.forClass(FavoriteGame.class);
            UUID selectedGameId = UUID.randomUUID();
            UUID currentUserId = UUID.randomUUID();
            GameId gameId = new GameId(selectedGameId);
            UserId userId = new UserId(currentUserId);
            FavoriteGame returnValue = new FavoriteGame(currentUserId, selectedGameId);
            Genre puzzle = new Genre(UUID.randomUUID(),"Puzzle","Genre where you solve puzzles");
            Game game1 = new Game("Tic Tac Toe", "Game where you...", 20, "testimg.png", "testicon.png", "localhost:8080", puzzle);
            Mockito.when(favoriteGameRepository.save(Mockito.any(FavoriteGame.class)))
                    .thenReturn(returnValue);
            Mockito.when(gameRepository.findById(selectedGameId))
                    .thenReturn(Optional.of(game1));

            // Act
            Game result = gameService.addFavoriteGame(gameId, userId);
            //Assert
            assertThat(result).isNotNull();
            assertThat(result.getName()).isEqualTo("Tic Tac Toe");
            Mockito.verify(favoriteGameRepository).save(favoriteGameCaptor.capture());
            FavoriteGame captured = favoriteGameCaptor.getValue();
            assertThat(captured.getFavoriteGameId().getUserId()).isEqualTo(currentUserId);
            assertThat(captured.getFavoriteGameId().getGameId()).isEqualTo(selectedGameId);
            Mockito.verify(gameRepository, times(2)).findById(selectedGameId);
            Mockito.verifyNoMoreInteractions(gameRepository);
            Mockito.verifyNoMoreInteractions(favoriteGameRepository);
        }

        @Test
        void removeExistingGameFavorite_Should_not_throw_error() {
            // Arrange
            ArgumentCaptor<FavoriteGame> favoriteGameCaptor = ArgumentCaptor.forClass(FavoriteGame.class);
            UUID selectedGameId = UUID.randomUUID();
            UUID currentUserId = UUID.randomUUID();
            GameId gameId = new GameId(selectedGameId);
            UserId userId = new UserId(currentUserId);
            Genre puzzle = new Genre(UUID.randomUUID(),"Puzzle","Genre where you solve puzzles");
            Game game1 = new Game("Tic Tac Toe", "Game where you...", 20, "testimg.png", "testicon.png", "localhost:8080", puzzle);

            Mockito.when(gameRepository.findById(selectedGameId))
                    .thenReturn(Optional.of(game1));

            // Act
            gameService.removeFavoriteGame(gameId, userId);

            //assert
            Mockito.verify(gameRepository, times(1)).findById(selectedGameId);
            Mockito.verify(favoriteGameRepository).remove(favoriteGameCaptor.capture());
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
            assertThatThrownBy(() -> gameService.addFavoriteGame(invalidGameId,new UserId(userId)))
                    .isInstanceOf(NotFoundException.class);

            Mockito.verify(gameRepository, times(1)).findById(invalidGameId.id());
            Mockito.verifyNoInteractions(favoriteGameRepository);
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
            assertThatThrownBy(() -> gameService.removeFavoriteGame(invalidGameId,new UserId(userId)))
                    .isInstanceOf(NotFoundException.class);

            Mockito.verify(gameRepository, times(1)).findById(invalidGameId.id());
            Mockito.verifyNoInteractions(favoriteGameRepository);
            Mockito.verifyNoMoreInteractions(gameRepository);
        }
    }
}
