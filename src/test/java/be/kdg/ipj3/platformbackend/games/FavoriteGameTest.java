package be.kdg.ipj3.platformbackend.games;

import be.kdg.ipj3.platformbackend.game.application.FavoriteGameService;
import be.kdg.ipj3.platformbackend.shared.domain.exception.NotFoundException;
import be.kdg.ipj3.platformbackend.game.domain.Game;
import be.kdg.ipj3.platformbackend.game.domain.GameId;
import be.kdg.ipj3.platformbackend.game.domain.Genre;
import be.kdg.ipj3.platformbackend.game.domain.repository.GameRepository;
import be.kdg.ipj3.platformbackend.shared.domain.UserId;
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
    FavoriteGameRepository favoriteGameRepository;

    @InjectMocks
    FavoriteGameService favoriteGameService;

    @Nested
    class SuccessFlows {
        @Test
        void addFavoriteExistingGame_Should_return_Game() {
            // Arrange
            ArgumentCaptor<FavoriteGame> favoriteGameCaptor = ArgumentCaptor.forClass(FavoriteGame.class);
            UUID selectedGameId = UUID.randomUUID();
            UUID currentUserId = UUID.randomUUID();
            GameId gameId = new GameId(selectedGameId);
            UserId userId = new UserId(currentUserId);
            FavoriteGame returnValue = new FavoriteGame(currentUserId, selectedGameId);
            Genre puzzle = new Genre("Puzzle","Genre where you solve puzzles");
            Game game1 = new Game(new GameId(),"Tic Tac Toe", "Game where you...", 20, "testimg.png", "testicon.png", "localhost:8080", puzzle, new ArrayList<>());
            Mockito.when(favoriteGameRepository.save(Mockito.any(FavoriteGame.class)))
                    .thenReturn(returnValue);
            Mockito.when(gameRepository.findById(selectedGameId))
                    .thenReturn(Optional.of(game1));

            // Act
            Game result = favoriteGameService.addFavoriteGame(gameId, userId);
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
            Genre puzzle = new Genre("Puzzle","Genre where you solve puzzles");
            Game game1 = new Game(new GameId(),"Tic Tac Toe", "Game where you...", 20, "testimg.png", "testicon.png", "localhost:8080", puzzle, new ArrayList<>());

            Mockito.when(gameRepository.findById(selectedGameId))
                    .thenReturn(Optional.of(game1));

            // Act
            favoriteGameService.removeFavoriteGame(gameId, userId);

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
            assertThatThrownBy(() -> favoriteGameService.addFavoriteGame(invalidGameId,new UserId(userId)))
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
            assertThatThrownBy(() -> favoriteGameService.removeFavoriteGame(invalidGameId,new UserId(userId)))
                    .isInstanceOf(NotFoundException.class);

            Mockito.verify(gameRepository, times(1)).findById(invalidGameId.id());
            Mockito.verifyNoInteractions(favoriteGameRepository);
            Mockito.verifyNoMoreInteractions(gameRepository);
        }
    }
}
