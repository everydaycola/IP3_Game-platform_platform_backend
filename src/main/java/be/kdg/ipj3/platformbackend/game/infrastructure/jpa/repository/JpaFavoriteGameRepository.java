package be.kdg.ipj3.platformbackend.game.infrastructure.jpa.repository;

import be.kdg.ipj3.platformbackend.game.domain.FavoriteGameId;
import be.kdg.ipj3.platformbackend.game.infrastructure.jpa.entity.JpaFavoriteGameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaFavoriteGameRepository extends JpaRepository<JpaFavoriteGameEntity, UUID> {
    List<JpaFavoriteGameEntity> findAllByFavoriteGameId_UserId(UUID userId);
    Optional<JpaFavoriteGameEntity> findByFavoriteGameId(FavoriteGameId searchedFavId);
}
