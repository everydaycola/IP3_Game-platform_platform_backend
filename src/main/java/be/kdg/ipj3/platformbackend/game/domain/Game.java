package be.kdg.ipj3.platformbackend.game.domain;

import be.kdg.ipj3.platformbackend.achievement.domain.Achievement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

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
    private List<Achievement> achievements;
}
