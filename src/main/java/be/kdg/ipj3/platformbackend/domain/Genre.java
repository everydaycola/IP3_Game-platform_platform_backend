package be.kdg.ipj3.platformbackend.domain;

import lombok.Getter;

@Getter
public class Genre {
    private String name;
    private String description;

    public Genre(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
