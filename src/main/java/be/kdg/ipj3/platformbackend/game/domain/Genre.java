package be.kdg.ipj3.platformbackend.game.domain;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Genre {

    private final String name;
    private final String description;

    public Genre(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
