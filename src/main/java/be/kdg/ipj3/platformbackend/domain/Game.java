package be.kdg.ipj3.platformbackend.domain;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class Game {
    private final GameId id;
    private String name;
    private String description;
    private double price;
    private String image;
    private String icon;
    private Genre genre;

    public Game(String name, String description, double price, String image, String icon, Genre genre) {
        this.id = new GameId();
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.icon = icon;
        this.genre = genre;
    }

    private Game(GameId id, String name, String description, double price, String image, String icon, Genre genre) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.icon = icon;
        this.genre = genre;
    }

    public static Game fromDb(GameId id, String name, String description, double price, String image, String icon, Genre genre){
        log.info("Returning game: {} from database.", name);
        return new Game(id,name,description,price,image,icon, genre);
    }

}
