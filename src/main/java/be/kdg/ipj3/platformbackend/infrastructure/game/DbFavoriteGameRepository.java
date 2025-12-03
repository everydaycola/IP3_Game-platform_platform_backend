package be.kdg.ipj3.platformbackend.infrastructure.game;

import be.kdg.ipj3.platformbackend.domain.game.FavoriteGame;
import be.kdg.ipj3.platformbackend.domain.repository.FavoriteGameRepository;
import be.kdg.ipj3.platformbackend.infrastructure.game.jpa.entity.game.JpaFavoriteGameEntity;
import be.kdg.ipj3.platformbackend.infrastructure.game.jpa.repository.JpaFavoriteGameRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class DbFavoriteGameRepository implements FavoriteGameRepository {

    private final JpaFavoriteGameRepository jpaFavoriteGameRepository;

    public DbFavoriteGameRepository(JpaFavoriteGameRepository jpaFavoriteGameRepository) {
        this.jpaFavoriteGameRepository = jpaFavoriteGameRepository;
    }

    @Override
    public FavoriteGame save(FavoriteGame favorite) {
        JpaFavoriteGameEntity result =  jpaFavoriteGameRepository.save(new JpaFavoriteGameEntity(favorite.getFavoriteGameId()));
        return result.toDomain();
    }

    @Override
    public void remove(FavoriteGame game) {

    }
}
