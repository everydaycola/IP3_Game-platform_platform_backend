package be.kdg.ipj3.platformbackend.game.infrastructure.jpa.entity;

import be.kdg.ipj3.platformbackend.achievement.infrastructure.jpa.JpaAchievementEntity;
import be.kdg.ipj3.platformbackend.game.domain.Game;
import be.kdg.ipj3.platformbackend.game.domain.GameId;
import jakarta.persistence.*;
import org.hibernate.Hibernate;

import java.util.ArrayList;
import java.util.List;
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

    @ManyToOne
    @JoinColumn(name = "genre_name", nullable = false)
    private JpaGenreEntity genre;

    @Column
    private String url;

    @OneToMany(mappedBy = "game" ,orphanRemoval = true, cascade = CascadeType.ALL)
    private List<JpaAchievementEntity> achievements;

    public JpaGameEntity() {
    }

    public JpaGameEntity(UUID id, String name, String description, double price, String image, String icon, String url, JpaGenreEntity genre) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.icon = icon;
        this.url = url;
        this.genre = genre;
    }

    public static JpaGameEntity fromDomain(Game game) {
        JpaGameEntity entity = new JpaGameEntity(
                game.getId().id(),
                game.getName(),
                game.getDescription(),
                game.getPrice(),
                game.getImage(),
                game.getIcon(),
                game.getUrl(),
                JpaGenreEntity.fromDomain(game.getGenre())
        );
        entity.achievements = game.getAchievements().stream()
                .map(achievement -> JpaAchievementEntity.fromDomain(achievement, entity))
                .toList();

        return entity;
    }

    public Game toDomain() {
        return new Game(
                new GameId(this.id),
                this.name,
                this.description,
                this.price,
                this.image,
                this.icon,
                this.url,
                this.genre.toDomain(),
                this.achievements.stream().map(JpaAchievementEntity::toDomain).toList()
        );
    }
}
