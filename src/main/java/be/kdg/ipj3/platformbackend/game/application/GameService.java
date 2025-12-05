package be.kdg.ipj3.platformbackend.game.application;

import be.kdg.ipj3.platformbackend.game.domain.Game;
import be.kdg.ipj3.platformbackend.game.domain.GameId;
import be.kdg.ipj3.platformbackend.game.domain.repository.FavoriteGameRepository;
import be.kdg.ipj3.platformbackend.game.domain.repository.GameRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class GameService {
    private final GameRepository gameRepository;
    private final FavoriteGameRepository favoriteGameRepository;

    public GameService(GameRepository games, FavoriteGameRepository favoriteGameRepository) {
        this.gameRepository = games;
        this.favoriteGameRepository = favoriteGameRepository;
    }

    public List<Game> findAll() {
        log.info("Returning all Games");
        return gameRepository.findAll();
    }

    public Game find(GameId gameId) {
        log.info("Finding game {}", gameId.id());
        return gameRepository.findById(gameId.id()).orElseThrow(gameId::notFound);
    }
}
