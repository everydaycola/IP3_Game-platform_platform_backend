package be.kdg.ipj3.platformbackend.analytics.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FriendAddedEvent extends BaseEvent {
    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("friend_id")
    private String friendId;

    @JsonProperty("connection_type")
    private String connectionType;

    public FriendAddedEvent(String userId, String friendId, String connectionType) {
        super("friend_added");
        this.userId = userId;
        this.friendId = friendId;
        this.connectionType = connectionType;
    }
}

