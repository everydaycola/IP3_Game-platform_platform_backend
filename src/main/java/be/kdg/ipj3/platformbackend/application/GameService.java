package be.kdg.ipj3.platformbackend.application;

import be.kdg.ipj3.platformbackend.domain.Game;
import be.kdg.ipj3.platformbackend.domain.GameRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class GameService {
    private final GameRepository games;

    public GameService(GameRepository games) {
        this.games = games;
    }

    public List<Game> findAll(){
        log.info("Returning all Games");
        return games.findAll();
    }
}
