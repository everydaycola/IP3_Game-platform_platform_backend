package be.kdg.ipj3.platformbackend.api.dtos;

import be.kdg.ipj3.platformbackend.domain.Game;

import java.util.UUID;

//A dto for a limited version of a game to put in a list, not a list of game dtos
public record GameListdto(UUID id, String name, String icon, String genre) {
    public static GameListdto from(final Game game){
        return new GameListdto(
                game.getId().id(),
                game.getName(),
                game.getIcon(),
                game.getGenre().getName()
        );
    }
}
