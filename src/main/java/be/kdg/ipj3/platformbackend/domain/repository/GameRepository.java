package be.kdg.ipj3.platformbackend.domain.repository;

import be.kdg.ipj3.platformbackend.domain.game.Game;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GameRepository {
    void save(Game game);
    List<Game> findAll();
    Optional<Game> findById(UUID id);

}
