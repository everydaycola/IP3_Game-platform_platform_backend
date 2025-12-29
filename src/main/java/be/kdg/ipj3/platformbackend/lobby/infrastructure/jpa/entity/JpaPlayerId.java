package be.kdg.ipj3.platformbackend.lobby.infrastructure.jpa.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;


@Embeddable
@Getter
@AllArgsConstructor
public class JpaPlayerId {
    private UUID userId;
    private UUID lobbyId;

    public JpaPlayerId() {
    }
}
