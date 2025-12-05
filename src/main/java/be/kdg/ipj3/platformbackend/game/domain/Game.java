package be.kdg.ipj3.platformbackend.game.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
@AllArgsConstructor
public class Game {
    private final GameId id;
    private final String name;
    private final String description;
    private final double price;
    private final String image;
    private final String icon;
    private final String url;
    private final Genre genre;
}
