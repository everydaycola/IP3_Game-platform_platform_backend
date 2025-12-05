package be.kdg.ipj3.platformbackend.user.infrastructure.jpa.entity;

import be.kdg.ipj3.platformbackend.user.domain.PlatformUserFriendId;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class JpaPlatformUserFriendId implements Serializable {
    UUID userId;
    UUID friendId;

    public JpaPlatformUserFriendId() {}

    public JpaPlatformUserFriendId(UUID userId, UUID friendId) {
        this.userId = userId;
        this.friendId = friendId;
    }

    public static JpaPlatformUserFriendId fromDomain(PlatformUserFriendId friendId) {
        return new JpaPlatformUserFriendId(
                friendId.getUserId(),
                friendId.getFriendId()
        );
    }
}