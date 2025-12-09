package be.kdg.ipj3.platformbackend.analytics.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GameStartedEvent extends BaseEvent {
    @JsonProperty("game_id")
    private String gameId;

    @JsonProperty("player_id")
    private String playerId;

    @JsonProperty("session_id")
    private String sessionId;

    @JsonProperty("game_name")
    private String gameName;

    @JsonProperty("player_count")
    private Integer playerCount;

    @JsonProperty("started_at")
    private String startedAt;

    public GameStartedEvent(String gameId, String playerId, String sessionId, String gameName, Integer playerCount, String startedAt) {
        super("game_started");
        this.gameId = gameId;
        this.playerId = playerId;
        this.sessionId = sessionId;
        this.gameName = gameName;
        this.playerCount = playerCount;
        this.startedAt = startedAt;
    }
}


