package be.kdg.ipj3.platformbackend.game.infrastructure.jpa.entity;

import be.kdg.ipj3.platformbackend.game.domain.Genre;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(
        name = "genres",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "name")
        }
)
public class JpaGenreEntity {
    @Id
    private UUID id;

    @Column
    private String name;

    @Column
    private String description;

    public JpaGenreEntity() {
    }

    public JpaGenreEntity(UUID id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public static JpaGenreEntity fromDomain(Genre genre) {
        return new JpaGenreEntity(
                genre.getId(),
                genre.getName(),
                genre.getDescription()
        );
    }

    public Genre toDomain() {
        return new Genre(
                this.id,
                this.name,
                this.description
        );
    }
}
