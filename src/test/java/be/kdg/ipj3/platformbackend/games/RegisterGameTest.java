package be.kdg.ipj3.platformbackend.games;

import be.kdg.ipj3.platformbackend.game.api.dtos.FullGameDto;
import be.kdg.ipj3.platformbackend.game.application.GameService;
import be.kdg.ipj3.platformbackend.game.domain.Game;
import be.kdg.ipj3.platformbackend.game.domain.GameId;
import be.kdg.ipj3.platformbackend.game.domain.Genre;
import be.kdg.ipj3.platformbackend.game.domain.repository.GameRepository;
import be.kdg.ipj3.platformbackend.shared.api.UrlChecker;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class RegisterGameTest {

    @Mock
    UrlChecker urlChecker;

    @Mock
    GameRepository gameRepository;

    @InjectMocks
    GameService gameService;

    @Nested
    class SuccessFlows {
        @Test
        void registerGame_registers_game_with_working_url_without_exceptions(){
            //Arrange
            Genre puzzle = new Genre("Puzzle","Genre where you solve puzzles");
            Game game1 = new Game(new GameId(),"Tic Tac Toe", "Game where you...", 20, "testimg.png", "testicon.png", "http://localhost:8080", puzzle);

            FullGameDto dto = FullGameDto.from(game1);

            Mockito.when(gameRepository.findGenre(puzzle.getName())).thenReturn(Optional.of(puzzle));
            Mockito.when(urlChecker.isUrlReachable(game1.getUrl())).thenReturn(true);

            //Act
            Game result = gameService.registerGame(dto);

            //Assert
            assertThat(result).isNotNull();
            assertThat(result.getName()).isEqualTo(game1.getName());
            assertThat(result.getGenre().getName()).isEqualTo(game1.getGenre().getName());
            Mockito.verify(gameRepository, Mockito.atMostOnce()).save(Mockito.any());
        }
    }

    @Nested
    class ErrorFlows{
        @Test
        void registerGame_does_not_register_game_when_url_is_unreachable(){
            //Arrange
            Genre puzzle = new Genre("Puzzle","Genre where you solve puzzles");
            Game game1 = new Game(new GameId(),"Tic Tac Toe", "Game where you...", 20, "testimg.png", "testicon.png", "http://localhost:8080", puzzle);

            FullGameDto dto = FullGameDto.from(game1);

            Mockito.when(gameRepository.findGenre(puzzle.getName())).thenReturn(Optional.of(puzzle));
            Mockito.when(urlChecker.isUrlReachable(game1.getUrl())).thenReturn(false);

            //Act
            Game result = gameService.registerGame(dto);

            //Assert
            assertThat(result).isNotNull();
            assertThat(result.getName()).isEqualTo(game1.getName());
            assertThat(result.getGenre().getName()).isEqualTo(game1.getGenre().getName());
            Mockito.verify(gameRepository, Mockito.never()).save(Mockito.any());
        }
    }
}
