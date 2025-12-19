package be.kdg.ipj3.platformbackend.user.infrastructure.jpa.entity;

import be.kdg.ipj3.platformbackend.game.domain.GameId;
import be.kdg.ipj3.platformbackend.user.domain.OwnedCopy;
import be.kdg.ipj3.platformbackend.user.domain.OwnedCopyId;
import jakarta.persistence.*;

import java.util.UUID;


@Entity
@Table(name = "owned_copies")
public class JpaOwnedCopyEntity {
    @EmbeddedId
    private UUID id;

    @JoinColumn(name = "game_id", nullable = false)
    private UUID gameId;

    @Column
    private boolean isFavorite;

    public JpaOwnedCopyEntity() {
    }

    public JpaOwnedCopyEntity(UUID id, UUID gameId, boolean isFavorite) {
        this.id = id;
        this.gameId = gameId;
        this.isFavorite = isFavorite;
    }

    public static JpaOwnedCopyEntity fromDomain(OwnedCopy ownedCopy) {
        return new JpaOwnedCopyEntity(ownedCopy.getId().id(), ownedCopy.getGameId().id(), ownedCopy.isFavorite());
    }

    public OwnedCopy toDomain() {
        return new OwnedCopy(new OwnedCopyId(id), new GameId(gameId), isFavorite);
    }
}
