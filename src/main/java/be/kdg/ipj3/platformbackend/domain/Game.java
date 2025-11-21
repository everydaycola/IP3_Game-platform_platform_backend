package be.kdg.ipj3.platformbackend.domain;

public class Game {
    private final GameId id;
    private String name;
    private String description;
    private double price;
    private String image;
    private String icon;

    public Game(GameId id, String name, String description, double price, String image, String icon) {
        this.id = new GameId();
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.icon = icon;
    }
}
