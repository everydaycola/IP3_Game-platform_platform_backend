package be.kdg.ipj3.platformbackend.games;
import be.kdg.ipj3.platformbackend.application.GameService;
import be.kdg.ipj3.platformbackend.domain.Game;
import be.kdg.ipj3.platformbackend.domain.GameRepository;
import be.kdg.ipj3.platformbackend.domain.Genre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetGameListTest {

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
        void getFullGamesList_returnsExpected() {
            //Arrange
            Genre puzzle = new Genre("Puzzle","Genre where you solve puzzles");
            Genre strategy = new Genre("Strategy","Genre where a good strategy is key.");
            List<Game> gameList = new ArrayList<Game>(
                    List.of(
                            new Game("Tic Tac Toe", "Game where you...", 20, "testimg.png", "testicon.png", "localhost:8080", puzzle),
                            new Game("Go", "Game where you...", 15, "testimg.png", "testicon.png", "localhost:8081", strategy),
                            new Game("Tetris", "Game where you...", 15, "testimg.png", "testicon.png", "localhost:8082", strategy)
                    )
            );
            Mockito.when(gameRepository.findAll()).thenReturn(gameList);

            //Act
            List<Game> result = gameService.findAll();

            //Assert
            assertThat(result.size()).isEqualTo(3);
            assertThat(result.get(0).getName()).isEqualTo("Tic Tac Toe");
            assertThat(result.get(1).getName()).isEqualTo("Go");
            assertThat(result.get(2).getName()).isEqualTo("Tetris");
            Mockito.verify(gameRepository, times(1)).findAll();
            Mockito.verifyNoMoreInteractions(gameRepository);
        }

        @Test
        void getFullGamesList_returnsEmptyList_WhenNoGamesAreRegistered(){
            //Arrange
            List<Game> gameList = new ArrayList<>();
            Mockito.when(gameRepository.findAll()).thenReturn(gameList);

            //Act
            List<Game> result = gameService.findAll();

            //Assert
            assertThat(result.size()).isEqualTo(0);
            Mockito.verify(gameRepository, times(1)).findAll();
            Mockito.verifyNoMoreInteractions(gameRepository);
        }
    }
}
