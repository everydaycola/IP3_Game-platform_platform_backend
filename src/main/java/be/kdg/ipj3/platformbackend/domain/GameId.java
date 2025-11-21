package be.kdg.ipj3.platformbackend.domain;

import java.util.UUID;

public record GameId(UUID id) {
    public GameId() { this(UUID.randomUUID());}
}
