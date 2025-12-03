package be.kdg.ipj3.platformbackend.domain.repository;


import be.kdg.ipj3.platformbackend.domain.game.FavoriteGame;

public interface FavoriteGameRepository {
    FavoriteGame save(FavoriteGame game);
    void remove(FavoriteGame game);
}
