package be.kdg.ipj3.platformbackend.lobby.infrastructure.jpa.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.UUID;


@Embeddable
@Getter
public class JpaPlayerId {
    private UUID userId;
    private UUID lobbyId;
}
