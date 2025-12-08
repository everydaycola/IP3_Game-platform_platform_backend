package be.kdg.ipj3.platformbackend.user.domain;

import lombok.Getter;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class PlatformUserFriend {
    private final PlatformUserFriendId id;
    private final Boolean isConfirmed;
    private final LocalDateTime requestedAt;
    private final LocalDateTime confirmedAt;
    private final UUID senderId;

    public PlatformUserFriend(PlatformUserFriendId id, Boolean isConfirmed, LocalDateTime requestedAt, LocalDateTime confirmedAt, UUID senderId) {
        this.id = id;
        this.isConfirmed = isConfirmed;
        this.requestedAt = requestedAt;
        this.confirmedAt = confirmedAt;
        this.senderId = senderId;
    }

    public static PlatformUserFriend fromDb(
            PlatformUserFriendId id,
            Boolean isConfirmed,
            LocalDateTime requestedAt,
            LocalDateTime confirmedAt,
            UUID senderId
    ) {
        return new PlatformUserFriend(id, isConfirmed, requestedAt, confirmedAt, senderId);
    }
}
