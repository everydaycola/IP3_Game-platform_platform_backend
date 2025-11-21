package be.kdg.ipj3.platformbackend.infrastructure.game.jpa;

import be.kdg.ipj3.platformbackend.domain.Game;
import be.kdg.ipj3.platformbackend.domain.GameId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "games")
public class JpaGameEntity {

    @Id
    @Column
    private UUID id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private double price;

    @Column
    private String image;

    @Column
    private String icon;

    public JpaGameEntity() {
    }

    public JpaGameEntity(UUID id, String name, String description, double price, String image, String icon) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.icon = icon;
    }

    public static JpaGameEntity fromDomain(Game game) {
        return new JpaGameEntity(
                game.getId().id(),
                game.getName(),
                game.getDescription(),
                game.getPrice(),
                game.getImage(),
                game.getIcon()
        );
    }

    public Game toDomain() {
        return Game.fromDb(
                new GameId(this.id),
                this.name,
                this.description,
                this.price,
                this.image,
                this.icon
        );
    }
}
