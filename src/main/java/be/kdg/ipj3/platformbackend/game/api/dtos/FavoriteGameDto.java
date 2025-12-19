package be.kdg.ipj3.platformbackend.game.api.dtos;

import java.util.UUID;

public record FavoriteGameDto(UUID id, UUID gameId) {
    public static FavoriteGameDto from(final FavoriteGame game){
        return new FavoriteGameDto(
                game.getFavoriteGameId().getUserId(),
                game.getFavoriteGameId().getGameId()
        );
    }
}
