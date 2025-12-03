package be.kdg.ipj3.platformbackend.domain.game;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Getter
@Slf4j
public class FavoriteGame {
    FavoriteGameId favoriteGameId;

    public FavoriteGame(UUID userId, UUID gameId) {
        this.favoriteGameId = new FavoriteGameId(userId, gameId);
    }

    public FavoriteGame(FavoriteGameId favoriteGameId) {
        this.favoriteGameId = favoriteGameId;
    }

    public static FavoriteGame fromDb(FavoriteGameId id) {
        log.info("Returning favoriteGame: {} from database.", id);
        return new FavoriteGame(id);
    }


}
