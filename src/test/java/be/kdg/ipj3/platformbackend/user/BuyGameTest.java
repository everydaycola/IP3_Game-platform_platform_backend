package be.kdg.ipj3.platformbackend.user;

import be.kdg.ipj3.platformbackend.game.domain.Game;
import be.kdg.ipj3.platformbackend.game.domain.GameId;
import be.kdg.ipj3.platformbackend.game.domain.Genre;
import be.kdg.ipj3.platformbackend.game.domain.repository.GameRepository;
import be.kdg.ipj3.platformbackend.shared.domain.UserId;
import be.kdg.ipj3.platformbackend.shared.domain.exception.InsufficientCreditsException;
import be.kdg.ipj3.platformbackend.user.application.UserService;
import be.kdg.ipj3.platformbackend.user.domain.OwnedCopy;
import be.kdg.ipj3.platformbackend.user.domain.PlatformUser;
import be.kdg.ipj3.platformbackend.user.domain.repository.PlatformUserRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class BuyGameTest {
    @Mock
    PlatformUserRepository platformUserRepository;

    @Mock
    GameRepository gameRepository;

    @InjectMocks
    UserService userService;

    @Nested
    class SuccessFlows {
        @Test
        void buyGame_shouldReturnOwnedCopyOfBoughtGame_whenUserHasSufficientCredits() {
            //Arrange
            UserId userId = new UserId(UUID.randomUUID());
            GameId gameId = new GameId(UUID.randomUUID());

            PlatformUser mockUser = new PlatformUser(userId, "userName1", "", new ArrayList<>(), "", "", 50.0, new ArrayList<>());
            Genre puzzle = new Genre("Puzzle", "Genre where you solve puzzles");
            Game game = new Game(gameId, "Tic Tac Toe", "Game where you...", 20, "testimg.png", "testicon.png", "localhost:8080", puzzle, new ArrayList<>());

            Mockito.when(platformUserRepository.findUserById(userId))
                    .thenReturn(Optional.of(mockUser));
            Mockito.when(gameRepository.findById(gameId.id()))
                    .thenReturn(Optional.of(game));

            //Act
            OwnedCopy ownedCopyResult = userService.buyGame(userId, gameId);

            //Assert
            assertThat(ownedCopyResult).isNotNull();
            assertThat(ownedCopyResult.getGameId().id()).isEqualTo(gameId.id());
            assertThat(mockUser.getOwnedGames().size()).isEqualTo(1);
            assertThat(mockUser.getCredits()).isEqualTo(30);
        }
    }

    @Nested
    class ErrorFlows {
        @Test
        void buyGame_shouldThrowInsufficientCreditsException_whenUserHasInsufficientCredits() {
            // Arrange
            UserId userId = new UserId(UUID.randomUUID());
            GameId gameId = new GameId(UUID.randomUUID());

            // User has only 10 credits
            PlatformUser mockUser = new PlatformUser(
                    userId,
                    "userName1",
                    "",
                    new ArrayList<>(),
                    "",
                    "",
                    10.0,
                    new ArrayList<>()
            );

            Genre puzzle = new Genre("Puzzle", "Genre where you solve puzzles");
            Game game = new Game(
                    gameId,
                    "Tic Tac Toe",
                    "Game where you...",
                    20,
                    "testimg.png",
                    "testicon.png",
                    "localhost:8080",
                    puzzle,
                    new ArrayList<>()
            );

            Mockito.when(platformUserRepository.findUserById(userId))
                    .thenReturn(Optional.of(mockUser));
            Mockito.when(gameRepository.findById(gameId.id()))
                    .thenReturn(Optional.of(game));

            // Act + Assert
            assertThatThrownBy(() -> userService.buyGame(userId, gameId))
                    .isInstanceOf(InsufficientCreditsException.class)
                    .hasMessageContaining("Not enough credits");
            assertThat(mockUser.getOwnedGames().size()).isEqualTo(0);
            assertThat(mockUser.getCredits()).isEqualTo(10);
        }
    }
}
