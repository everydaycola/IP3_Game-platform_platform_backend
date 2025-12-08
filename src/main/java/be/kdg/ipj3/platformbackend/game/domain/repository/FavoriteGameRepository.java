package be.kdg.ipj3.platformbackend.game.domain.repository;


import be.kdg.ipj3.platformbackend.game.domain.FavoriteGame;
import be.kdg.ipj3.platformbackend.shared.domain.UserId;

import java.util.List;
import java.util.UUID;

public interface FavoriteGameRepository {
    FavoriteGame save(FavoriteGame game);
    void remove(FavoriteGame game);
    List<FavoriteGame> findAllByUserId(UserId userId);
    FavoriteGame findFavorite(UserId userId, UUID gameId);
}
