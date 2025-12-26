package be.kdg.ipj3.platformbackend.lobby.infrastructure.jpa.repository;

import be.kdg.ipj3.platformbackend.lobby.infrastructure.jpa.entity.JpaLobbyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaLobbyRepository extends JpaRepository<JpaLobbyEntity, UUID> {
}
