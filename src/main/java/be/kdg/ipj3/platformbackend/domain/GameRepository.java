package be.kdg.ipj3.platformbackend.domain;

import java.util.List;

public interface GameRepository {
    void save(Game game);
    List<Game> findAll();
}
