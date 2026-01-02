package be.kdg.ipj3.platformbackend.lobby.infrastructure.game;

import be.kdg.ipj3.platformbackend.game.domain.Game;
import be.kdg.ipj3.platformbackend.lobby.api.dtos.request.StartGameRequest;
import be.kdg.ipj3.platformbackend.lobby.domain.catalog.GameApiCatalog;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Slf4j
@Component
public class ExternalGameApiCatalog implements GameApiCatalog {
    private final RestClient restClient;

    public ExternalGameApiCatalog(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public UUID startGameSession(Game game, String authToken, UUID player1Id, UUID player2Id) {
        log.info("Requesting a new game session");
        try{
            JsonNode responseBody = restClient.post()
                    .uri(game.getGameStartEndpoint())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + authToken)
                    .body(new StartGameRequest(player1Id, player2Id))
                    .retrieve()
                    .body(JsonNode.class);

            if (responseBody == null || !responseBody.has("id")) {
                throw new IllegalStateException(
                        "Game started but no 'id' field returned from " + game.getGameStartEndpoint()
                );
            }

            return UUID.fromString(responseBody.get("id").asText());

        }catch(HttpStatusCodeException e){
            log.warn("Http error while asking {} to start a new game session.", game.getName());
        }
        return null;
    }
}
