package be.kdg.ipj3.platformbackend.games;

import be.kdg.ipj3.platformbackend.achievement.domain.Achievement;
import be.kdg.ipj3.platformbackend.achievement.domain.AchievementId;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

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
            List<Achievement> achievements = new ArrayList<>(
                    List.of(
                            new Achievement(new AchievementId(UUID.randomUUID()),"King of the Hill","Win with starting from the middle"),
                            new Achievement(new AchievementId(UUID.randomUUID()),"King of the Corner","Win with starting from a corner")
                    )
            );
            Game game1 = new Game(new GameId(),"Tic Tac Toe", "Game where you...", 20, "testimg.png", "testicon.png", "http://localhost:8080", puzzle, achievements);

            FullGameDto dto = FullGameDto.from(game1);

            Mockito.when(gameRepository.findGenre(puzzle.getName())).thenReturn(Optional.of(puzzle));
            Mockito.when(urlChecker.isUrlReachable(game1.getUrl())).thenReturn(true);

            //Act
            Game result = gameService.registerGame(dto);

            //Assert
            assertThat(result).isNotNull();
            assertThat(result.getName()).isEqualTo(game1.getName());
            assertThat(result.getGenre().getName()).isEqualTo(game1.getGenre().getName());
            assertThat(result.getAchievements().size()).isEqualTo(2);
            assertThat(result.getAchievements().getFirst().getName()).isEqualTo(achievements.getFirst().getName());
            verify(gameRepository, Mockito.atLeastOnce()).save(Mockito.any());
        }
    }

    @Nested
    class ErrorFlows{
        @Test
        void registerGame_does_not_register_game_when_url_is_unreachable(){
            //Arrange
            Genre puzzle = new Genre("Puzzle","Genre where you solve puzzles");
            Game game1 = new Game(new GameId(),"Tic Tac Toe", "Game where you...", 20, "testimg.png", "testicon.png", "http://localhost:8080", puzzle, new ArrayList<>());

            FullGameDto dto = FullGameDto.from(game1);

            Mockito.when(gameRepository.findGenre(puzzle.getName())).thenReturn(Optional.of(puzzle));
            Mockito.when(urlChecker.isUrlReachable(game1.getUrl())).thenReturn(false);

            //Act
            Game result = gameService.registerGame(dto);

            //Assert
            assertThat(result).isNotNull();
            assertThat(result.getName()).isEqualTo(game1.getName());
            assertThat(result.getGenre().getName()).isEqualTo(game1.getGenre().getName());
            verify(gameRepository, Mockito.never()).save(Mockito.any());
        }
    }
}
