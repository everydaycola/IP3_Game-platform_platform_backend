package be.kdg.ipj3.platformbackend.game.domain;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class Game {
    private final GameId id;
    private final String name;
    private final String description;
    private final double price;
    private final String image;
    private final String icon;
    private final String url;
    private final Genre genre;

    public Game(String name, String description, double price, String image, String icon, String url, Genre genre) {
        this.url = url;
        this.id = new GameId();
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.icon = icon;
        this.genre = genre;
    }

    private Game(GameId id, String name, String description, double price, String image, String icon, String url, Genre genre) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.icon = icon;
        this.url = url;
        this.genre = genre;
    }

    public static Game fromDb(GameId id, String name, String description, double price, String image, String icon, Genre genre, String url) {
        log.info("Returning game: {} from database.", name);
        return new Game(id, name, description, price, image, icon, url, genre);
    }

}
