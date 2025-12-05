package be.kdg.ipj3.platformbackend.game.application;

import be.kdg.ipj3.platformbackend.game.domain.Game;
import be.kdg.ipj3.platformbackend.game.domain.GameId;
import be.kdg.ipj3.platformbackend.game.domain.repository.GameRepository;
import be.kdg.ipj3.platformbackend.shared.domain.UserId;
import be.kdg.ipj3.platformbackend.game.domain.FavoriteGame;
import be.kdg.ipj3.platformbackend.game.domain.repository.FavoriteGameRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FavoriteGameService {
    private final GameRepository gameRepository;
    private final FavoriteGameRepository favoriteGameRepository;

    public FavoriteGameService(GameRepository games, FavoriteGameRepository favoriteGameRepository) {
        this.gameRepository = games;
        this.favoriteGameRepository = favoriteGameRepository;
    }

    public Game addFavoriteGame(GameId gameId, UserId userId) {
        log.info("User: " + userId + " added game with id" + gameId + "to their favorites.");
        FavoriteGame favoriteGame = new FavoriteGame(userId.id(), gameId.id());
        gameRepository.findById(gameId.id()).orElseThrow(gameId::notFound);
        FavoriteGame saved = favoriteGameRepository.save(favoriteGame);
        return gameRepository.findById(saved.getFavoriteGameId().getGameId()).orElseThrow(gameId::notFound);
    }

    public void removeFavoriteGame(GameId gameId, UserId userId) {
        log.info("User: " + userId + " removed game with id" + gameId + "from their favorites.");
        FavoriteGame favoriteGame = new FavoriteGame(userId.id(), gameId.id());
        gameRepository.findById(gameId.id()).orElseThrow(gameId::notFound);
        favoriteGameRepository.remove(favoriteGame);
    }
}
