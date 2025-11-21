package be.kdg.ipj3.platformbackend.domain;

import java.util.List;
import java.util.Optional;

public interface GameRepository {
    void save(Game game);
    Optional<List<Game>> FindAll();
}
