package be.kdg.ipj3.platformbackend.game.infrastructure;

import be.kdg.ipj3.platformbackend.game.domain.FavoriteGame;
import be.kdg.ipj3.platformbackend.game.domain.FavoriteGameId;
import be.kdg.ipj3.platformbackend.game.domain.repository.FavoriteGameRepository;
import be.kdg.ipj3.platformbackend.game.infrastructure.jpa.entity.JpaFavoriteGameEntity;
import be.kdg.ipj3.platformbackend.game.infrastructure.jpa.repository.JpaFavoriteGameRepository;
import be.kdg.ipj3.platformbackend.shared.domain.UserId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

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
        jpaFavoriteGameRepository.delete(new JpaFavoriteGameEntity(game.getFavoriteGameId()));
    }

    @Override
    public List<FavoriteGame> findAllByUserId(UserId userId) {
        return jpaFavoriteGameRepository.findAllByFavoriteGameId_UserId(userId.id())
                .stream()
                .map(JpaFavoriteGameEntity::toDomain)
                .toList();
    }

    @Override
    public FavoriteGame findFavorite(UserId userId, UUID gameId) {
        FavoriteGameId searchedFavId = new FavoriteGameId(userId.id(), gameId);
        return jpaFavoriteGameRepository.findByFavoriteGameId(searchedFavId).orElseThrow(searchedFavId::notFound).toDomain();
    }
}
