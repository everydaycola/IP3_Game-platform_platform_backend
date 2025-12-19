package be.kdg.ipj3.platformbackend.user.domain;

import be.kdg.ipj3.platformbackend.game.domain.GameId;
import be.kdg.ipj3.platformbackend.shared.domain.exception.NotFoundException;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Slf4j
public record OwnedCopyId(GameId gameId) implements Serializable {

    public NotFoundException notFound() {
        log.error("Game with id {} not found in favorites.", gameId);
        return new NotFoundException("Game [" + gameId + "] not found as favorites");
    }

}
