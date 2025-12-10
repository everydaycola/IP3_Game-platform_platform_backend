package be.kdg.ipj3.platformbackend.analytics.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserLoggedInEvent extends BaseEvent {
    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("session_id")
    private String sessionId;

    @JsonProperty("login_method")
    private String loginMethod;

    @JsonProperty("device_type")
    private String deviceType;

    public UserLoggedInEvent(String userId, String sessionId, String loginMethod, String deviceType) {
        super("user_logged_in");
        this.userId = userId;
        this.sessionId = sessionId;
        this.loginMethod = loginMethod;
        this.deviceType = deviceType;
    }
}


