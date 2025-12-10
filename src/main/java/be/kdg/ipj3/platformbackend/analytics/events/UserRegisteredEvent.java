package be.kdg.ipj3.platformbackend.analytics.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserRegisteredEvent extends BaseEvent {
    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("registration_method")
    private String registrationMethod;

    public UserRegisteredEvent(String userId, String registrationMethod) {
        super("user_registered");
        this.userId = userId;
        this.registrationMethod = registrationMethod;
    }
}


