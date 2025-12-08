package be.kdg.ipj3.platformbackend.game.domain;

import be.kdg.ipj3.platformbackend.shared.domain.exception.NotFoundException;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Embeddable
@Slf4j
public class FavoriteGameId implements Serializable {
    private UUID userId;
    private UUID gameId;

    public FavoriteGameId() {
    }

    public FavoriteGameId(UUID userId, UUID gameId) {
        this.userId = userId;
        this.gameId = gameId;
    }

    public NotFoundException notFound() {
        log.error("Game with id {} not found in favorites for {}", userId, gameId);
        return new NotFoundException("Game [" + gameId + "] not found as favorite for user [" + userId + "]");
    }

}
