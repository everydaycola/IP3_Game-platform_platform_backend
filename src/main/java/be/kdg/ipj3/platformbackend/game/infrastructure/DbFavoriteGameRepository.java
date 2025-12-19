package be.kdg.ipj3.platformbackend.game.infrastructure;

import be.kdg.ipj3.platformbackend.user.domain.OwnedCopyId;
import be.kdg.ipj3.platformbackend.user.infrastructure.jpa.entity.JpaOwnedCopyEntity;
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
        JpaOwnedCopyEntity result =  jpaFavoriteGameRepository.save(new JpaOwnedCopyEntity(favorite.getFavoriteGameId()));
        return result.toDomain();
    }

    @Override
    public void remove(FavoriteGame game) {
        jpaFavoriteGameRepository.delete(new JpaOwnedCopyEntity(game.getFavoriteGameId()));
    }

    @Override
    public List<FavoriteGame> findAllByUserId(UserId userId) {
        return jpaFavoriteGameRepository.findAllByFavoriteGameId_UserId(userId.id())
                .stream()
                .map(JpaOwnedCopyEntity::toDomain)
                .toList();
    }

    @Override
    public FavoriteGame findFavorite(UserId userId, UUID gameId) {
        OwnedCopyId searchedFavId = new OwnedCopyId(userId.id(), gameId);
        return jpaFavoriteGameRepository.findByFavoriteGameId(searchedFavId).orElseThrow(searchedFavId::notFound).toDomain();
    }
}
