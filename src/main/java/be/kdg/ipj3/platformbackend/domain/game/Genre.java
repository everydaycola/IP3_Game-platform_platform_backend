package be.kdg.ipj3.platformbackend.domain.game;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Genre {
    private final UUID id;
    private final String name;
    private final String description;

    public Genre(UUID id,String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
