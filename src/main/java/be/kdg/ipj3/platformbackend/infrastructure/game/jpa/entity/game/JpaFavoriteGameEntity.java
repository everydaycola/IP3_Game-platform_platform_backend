package be.kdg.ipj3.platformbackend.infrastructure.game.jpa.entity.game;

import be.kdg.ipj3.platformbackend.domain.game.FavoriteGame;
import be.kdg.ipj3.platformbackend.domain.game.FavoriteGameId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;


@Entity
@Table(name = "favorite_game")
public class JpaFavoriteGameEntity {
    @EmbeddedId
    private FavoriteGameId favoriteGameId;

    public JpaFavoriteGameEntity() {
    }

    public JpaFavoriteGameEntity(FavoriteGameId favoriteGameId) {
        this.favoriteGameId = favoriteGameId;
    }

    public FavoriteGame toDomain() {
        return FavoriteGame.fromDb(
                this.favoriteGameId
        );
    }
}
