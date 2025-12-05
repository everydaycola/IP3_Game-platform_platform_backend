package be.kdg.ipj3.platformbackend.game.domain.repository;


import be.kdg.ipj3.platformbackend.game.domain.FavoriteGame;

public interface FavoriteGameRepository {
    FavoriteGame save(FavoriteGame game);
    void remove(FavoriteGame game);
}
