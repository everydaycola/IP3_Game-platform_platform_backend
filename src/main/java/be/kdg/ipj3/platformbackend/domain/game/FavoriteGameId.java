package be.kdg.ipj3.platformbackend.domain.game;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Embeddable
public class FavoriteGameId implements Serializable {
    private UUID userId;
    private UUID gameId;

    public FavoriteGameId() {
    }

    public FavoriteGameId(UUID userId, UUID gameId) {
        this.userId = userId;
        this.gameId = gameId;
    }
}
