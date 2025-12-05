package be.kdg.ipj3.platformbackend.user.domain;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;


@Getter
@Slf4j
public class PlatformUserFriendId {
    private final UUID userId;
    private final UUID friendId;

    public PlatformUserFriendId(UUID userId, UUID friendId) {
        this.userId = userId;
        this.friendId = friendId;
    }

    public static PlatformUserFriendId fromDb(UUID userId, UUID friendId) {
        return new PlatformUserFriendId(userId, friendId);
    }

}