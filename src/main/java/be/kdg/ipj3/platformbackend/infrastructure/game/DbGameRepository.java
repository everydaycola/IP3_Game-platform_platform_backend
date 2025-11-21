package be.kdg.ipj3.platformbackend.infrastructure.game;

import be.kdg.ipj3.platformbackend.domain.Game;
import be.kdg.ipj3.platformbackend.domain.GameRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class DbGameRepository implements GameRepository {

    @Override
    public void save(Game game) {

    }

    @Override
    public Optional<List<Game>> FindAll() {
        return Optional.empty();
    }
}
