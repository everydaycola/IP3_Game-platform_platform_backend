package be.kdg.ipj3.platformbackend.infrastructure.game;

import be.kdg.ipj3.platformbackend.domain.Game;
import be.kdg.ipj3.platformbackend.domain.GameRepository;
import be.kdg.ipj3.platformbackend.infrastructure.game.jpa.JpaGameEntity;
import be.kdg.ipj3.platformbackend.infrastructure.game.jpa.JpaGameRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@Slf4j
public class DbGameRepository implements GameRepository {

    private final JpaGameRepository jpaGameRepository;

    public DbGameRepository(JpaGameRepository jpaGameRepository) {
        this.jpaGameRepository = jpaGameRepository;
    }

    @Override
    public void save(Game game) {

    }

    @Override
    public List<Game> findAll() {
        return jpaGameRepository.findAll().stream().map(JpaGameEntity::toDomain).toList();
    }
}
