package be.kdg.ipj3.platformbackend.infrastructure.game;

import be.kdg.ipj3.platformbackend.domain.game.Game;
import be.kdg.ipj3.platformbackend.domain.repository.GameRepository;
import be.kdg.ipj3.platformbackend.infrastructure.game.jpa.entity.game.JpaGameEntity;
import be.kdg.ipj3.platformbackend.infrastructure.game.jpa.repository.JpaGameRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    @Override
    public Optional<Game> findById(UUID id) {
        return jpaGameRepository.findById(id).map(JpaGameEntity::toDomain);
    }



}
