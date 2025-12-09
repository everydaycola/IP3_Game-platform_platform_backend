package be.kdg.ipj3.platformbackend.analytics.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GamePageVisitEvent extends BaseEvent {
    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("game_id")
    private String gameId;

    @JsonProperty("game_name")
    private String gameName;

    @JsonProperty("referrer")
    private String referrer;

    @JsonProperty("session_id")
    private String sessionId;

    public GamePageVisitEvent(String userId, String gameId, String gameName, String referrer, String sessionId) {
        super("gamePage_visit");
        this.userId = userId;
        this.gameId = gameId;
        this.gameName = gameName;
        this.referrer = referrer;
        this.sessionId = sessionId;
    }
}

