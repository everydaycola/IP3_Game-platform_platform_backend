package be.kdg.ipj3.platformbackend.infrastructure.game.jpa;

import be.kdg.ipj3.platformbackend.domain.Genre;
import jakarta.persistence.*;

import java.util.List;
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

    @OneToMany(mappedBy = "genre", orphanRemoval = true)
    private List<JpaGameEntity> games;

    public JpaGenreEntity() {
    }

    public JpaGenreEntity(UUID id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public static JpaGenreEntity fromDomain(Genre genre) {
        return new JpaGenreEntity(
                UUID.randomUUID(),
                genre.getName(),
                genre.getDescription()
        );
    }

    public Genre toDomain() {
        return new Genre(
                this.name,
                this.description
        );
    }
}
