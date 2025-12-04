package be.kdg.ipj3.platformbackend.game.infrastructure.jpa.repository;

import be.kdg.ipj3.platformbackend.game.infrastructure.jpa.entity.JpaFavoriteGameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaFavoriteGameRepository extends JpaRepository<JpaFavoriteGameEntity, UUID> {
}
