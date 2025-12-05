package be.kdg.ipj3.platformbackend.game.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

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

    public boolean isUrlReachable() {
        log.info("Checking if the url: {} of game: {} is reachable",this.url, this.id);
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .timeout(Duration.ofSeconds(5))
                .GET()
                .build();

        try {
            return client.send(request, HttpResponse.BodyHandlers.discarding()).statusCode() == 200;
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Url: {} of game: {} is unreachable", this.url, this.id);
            return false;
        }
    }
}
