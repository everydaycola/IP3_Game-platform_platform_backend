package be.kdg.ipj3.platformbackend.user.infrastructure.jpa.entity;

import be.kdg.ipj3.platformbackend.game.domain.GameId;
import be.kdg.ipj3.platformbackend.user.domain.OwnedCopy;
import be.kdg.ipj3.platformbackend.user.domain.OwnedCopyId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.UUID;


@Entity
@Table(name = "favorite_game")
public class JpaOwnedCopyEntity {
    @EmbeddedId
    private UUID favoriteGameId;

    @Column
    private boolean isFavorite;

    public JpaOwnedCopyEntity() {
    }

    public JpaOwnedCopyEntity(UUID favoriteGameId, boolean isFavorite) {
        this.favoriteGameId = favoriteGameId;
        this.isFavorite = isFavorite;
    }

    public OwnedCopy toDomain() {
        return new OwnedCopy(new OwnedCopyId(new GameId(favoriteGameId)),isFavorite);
    }
}
