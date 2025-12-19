package be.kdg.ipj3.platformbackend.game.infrastructure.jpa.repository;

import be.kdg.ipj3.platformbackend.user.domain.OwnedCopyId;
import be.kdg.ipj3.platformbackend.user.infrastructure.jpa.entity.JpaOwnedCopyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaFavoriteGameRepository extends JpaRepository<JpaOwnedCopyEntity, UUID> {
    List<JpaOwnedCopyEntity> findAllByFavoriteGameId_UserId(UUID userId);
    Optional<JpaOwnedCopyEntity> findByFavoriteGameId(OwnedCopyId searchedFavId);
}
