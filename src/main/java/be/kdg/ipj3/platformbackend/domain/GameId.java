package be.kdg.ipj3.platformbackend.domain;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public record GameId(UUID id) {
    public GameId() { this(UUID.randomUUID());}

    public NotFoundException notFound() {
        log.error("Game with id {} not found", id);
        return new NotFoundException("Game [" + id + "] not found");
    }
}
