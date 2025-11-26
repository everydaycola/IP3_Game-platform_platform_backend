package be.kdg.ipj3.platformbackend.api.dtos;

import be.kdg.ipj3.platformbackend.domain.Game;

import java.util.UUID;

public record FullGameDto(UUID id, String name, String description, double price, String image, String icon, String genre, String url) {
    public static FullGameDto from(final Game game){
        return new FullGameDto(
                game.getId().id(),
                game.getName(),
                game.getDescription(),
                game.getPrice(),
                game.getImage(),
                game.getIcon(),
                game.getGenre().getName(),
                game.getUrl()
        );
    }
}
