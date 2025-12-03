package be.kdg.ipj3.platformbackend.application;

import be.kdg.ipj3.platformbackend.domain.game.FavoriteGame;
import be.kdg.ipj3.platformbackend.domain.game.Game;
import be.kdg.ipj3.platformbackend.domain.game.GameId;
import be.kdg.ipj3.platformbackend.domain.repository.FavoriteGameRepository;
import be.kdg.ipj3.platformbackend.domain.repository.GameRepository;
import be.kdg.ipj3.platformbackend.domain.user.UserId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class GameService {
    private final GameRepository games;
    private final FavoriteGameRepository favoriteGameRepository;

    public GameService(GameRepository games, FavoriteGameRepository favoriteGameRepository) {
        this.games = games;
        this.favoriteGameRepository = favoriteGameRepository;
    }

    public List<Game> findAll() {
        log.info("Returning all Games");
        return games.findAll();
    }

    public Game find(GameId gameId) {
        log.info("Finding game {}", gameId.id());
        return games.findById(gameId.id()).orElseThrow(gameId::notFound);
    }

    public Game addFavoriteGame(GameId gameId, UserId userId) {
        log.info("User: " + userId + " added game with id" + gameId + "to their favorites.");
        FavoriteGame favoriteGame = new FavoriteGame(userId.id(), gameId.id());
        FavoriteGame saved = favoriteGameRepository.save(favoriteGame);
        return games.findById(saved.getFavoriteGameId().getGameId()).orElseThrow(gameId::notFound);
    }

    public void removeFavoriteGame(GameId gameId, UserId userId) {
        log.info("User: " + userId + " removed game with id" + gameId + "to their favorites.");
        FavoriteGame favoriteGame = new FavoriteGame(userId.id(), gameId.id());
        favoriteGameRepository.remove(favoriteGame);
    }
}
