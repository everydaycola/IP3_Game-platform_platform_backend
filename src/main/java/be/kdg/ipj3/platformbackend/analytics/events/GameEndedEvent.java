package be.kdg.ipj3.platformbackend.analytics.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GameEndedEvent extends BaseEvent {
    @JsonProperty("game_id")
    private String gameId;

    @JsonProperty("player_id")
    private String playerId;

    @JsonProperty("session_id")
    private String sessionId;

    @JsonProperty("session_duration")
    private Integer sessionDuration;

    @JsonProperty("completed")
    private Boolean completed;

    @JsonProperty("ended_at")
    private String endedAt;

    public GameEndedEvent(String gameId, String playerId, String sessionId, Integer sessionDuration, Boolean completed, String endedAt) {
        super("game_ended");
        this.gameId = gameId;
        this.playerId = playerId;
        this.sessionId = sessionId;
        this.sessionDuration = sessionDuration;
        this.completed = completed;
        this.endedAt = endedAt;
    }
}

