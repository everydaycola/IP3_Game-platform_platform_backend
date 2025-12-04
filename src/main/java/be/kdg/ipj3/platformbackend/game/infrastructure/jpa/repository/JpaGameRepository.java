package be.kdg.ipj3.platformbackend.game.infrastructure.jpa.repository;

import be.kdg.ipj3.platformbackend.game.infrastructure.jpa.entity.JpaGameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaGameRepository extends JpaRepository<JpaGameEntity, UUID> {
}
