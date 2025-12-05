package be.kdg.ipj3.platformbackend.game.application;

import be.kdg.ipj3.platformbackend.game.api.dtos.FullGameDto;
import be.kdg.ipj3.platformbackend.game.domain.Game;
import be.kdg.ipj3.platformbackend.game.domain.GameId;
import be.kdg.ipj3.platformbackend.game.domain.Genre;
import be.kdg.ipj3.platformbackend.game.domain.repository.GameRepository;
import be.kdg.ipj3.platformbackend.shared.api.UrlChecker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class GameService {
    private final GameRepository gameRepository;

    public GameService(GameRepository games) {
        this.gameRepository = games;
    }

    public List<Game> findAll() {
        log.info("Returning all Games");
        return gameRepository.findAll();
    }

    public Game find(GameId gameId) {
        log.info("Finding game {}", gameId.id());
        return gameRepository.findById(gameId.id()).orElseThrow(gameId::notFound);
    }

    private Genre findGenre(String name){
        log.info("Finding genre {}", name);
        return gameRepository.findGenre(name).orElseThrow(Genre::notFound);
    }

    public Game registerGame(FullGameDto gameDto){
        log.info("Registering Game: {}, id: {}", gameDto.name(), gameDto.id());
        Game game = new Game(
                new GameId(gameDto.id()),
                gameDto.name(),
                gameDto.description(),
                gameDto.price(),
                gameDto.image(),
                gameDto.icon(),
                gameDto.url(),
                findGenre(gameDto.genre())
                );
        UrlChecker urlChecker = new UrlChecker();
        if (urlChecker.isUrlReachable(game.getUrl())){
            gameRepository.save(game);
        } else {
            log.error("Game: {} not registered due to url not being reachable", game.getUrl());
        }
        return game;
    }
}
