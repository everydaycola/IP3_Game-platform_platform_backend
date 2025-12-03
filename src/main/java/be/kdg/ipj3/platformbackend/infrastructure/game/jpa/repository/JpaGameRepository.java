package be.kdg.ipj3.platformbackend.infrastructure.game.jpa.repository;

import be.kdg.ipj3.platformbackend.infrastructure.game.jpa.entity.game.JpaGameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaGameRepository extends JpaRepository<JpaGameEntity, UUID> {
}
