package be.kdg.ipj3.platformbackend.games;

import be.kdg.ipj3.platformbackend.application.GameService;
import be.kdg.ipj3.platformbackend.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class GetGameTest {

    @Mock
    GameRepository gameRepository;

    GameService gameService;

    @BeforeEach
    public void setup() {
        gameService = new GameService(gameRepository);
    }

    @Nested
    class SuccesFlows {
        @Test
        void getGame_byExistingId_returnsGame() {
            //Arrange
            Genre puzzle = new Genre("Puzzle","Genre where you solve puzzles");
            Genre strategy = new Genre("Strategy","Genre where a good strategy is key.");

            Game game1 = new Game("Tic Tac Toe", "Game where you...", 20, "testimg.png", "testicon.png", "localhost:8080", puzzle);
            Game game2 = new Game("Go", "Game where you...", 15, "testimg.png", "testicon.png", "localhost:8081", strategy);
            Game game3 =  new Game("Tetris", "Game where you...", 15, "testimg.png", "testicon.png", "localhost:8082", strategy);

            List<Game> gameList = new ArrayList<Game>(
                    List.of(game1,game2,game3)
            );
            Mockito.when(gameRepository.findById(game1.getId().id())).thenReturn(Optional.of(game1));

            //Act
            Game result = gameService.find(game1.getId());

            //Assert
            assertThat(result).isNotNull();
            assertThat(result.getName()).isEqualTo("Tic Tac Toe");
            assertThat(result.getPrice()).isEqualTo(20);
            assertThat(result.getImage()).isEqualTo("testimg.png");
            Mockito.verify(gameRepository, times(1)).findById(game1.getId().id());
            Mockito.verifyNoMoreInteractions(gameRepository);
        }
    }

    @Nested
    class ErrorFlows{
        @Test
        void getGame_byNonExistingId_throws404() {
            // Arrange
            String uuid = "00000000-0000-0000-0000-000000000000";
            GameId invalidGameId = new GameId(UUID.fromString(uuid));

            Mockito.when(gameRepository.findById(invalidGameId.id()))
                    .thenReturn(Optional.empty());

            // Act & Assert
            assertThatThrownBy(() -> gameService.find(invalidGameId))
                    .isInstanceOf(NotFoundException.class);

            Mockito.verify(gameRepository, times(1)).findById(invalidGameId.id());
            Mockito.verifyNoMoreInteractions(gameRepository);
        }
    }
}
