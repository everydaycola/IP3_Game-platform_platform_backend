package be.kdg.ipj3.platformbackend.user.domain;

import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class PlatformUserFriend {
    private final PlatformUserFriendId id;
    private final Boolean isConfirmed;
    private final LocalDateTime requestedAt;
    private final LocalDateTime confirmedAt;

    public PlatformUserFriend(PlatformUserFriendId id, Boolean isConfirmed, LocalDateTime requestedAt, LocalDateTime confirmedAt) {
        this.id = id;
        this.isConfirmed = isConfirmed;
        this.requestedAt = requestedAt;
        this.confirmedAt = confirmedAt;
    }

    public static PlatformUserFriend fromDb(
            PlatformUserFriendId id,
            Boolean isConfirmed,
            LocalDateTime requestedAt,
            LocalDateTime confirmedAt
    ) {
        return new PlatformUserFriend(id, isConfirmed, requestedAt, confirmedAt);
    }
}
