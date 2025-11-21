package be.kdg.ipj3.platformbackend.domain;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Game {
    private final GameId id;
    private String name;
    private String description;
    private double price;
    private String image;
    private String icon;

    public Game(String name, String description, double price, String image, String icon) {
        this.id = new GameId();
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.icon = icon;
    }

    private Game(GameId id, String name, String description, double price, String image, String icon) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.icon = icon;
    }

    public static Game fromDb(GameId id, String name, String description, double price, String image, String icon){
        log.info("Returning game: {} from database.", name);
        return new Game(id,name,description,price,image,icon);
    }

    public GameId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public String getIcon() {
        return icon;
    }
}
