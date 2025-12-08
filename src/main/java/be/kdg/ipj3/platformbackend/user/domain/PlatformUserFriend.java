package be.kdg.ipj3.platformbackend.user.domain;

import lombok.Getter;
import java.time.LocalDateTime;
import java.util.UUID;

//Todo: rename again
@Getter
public class PlatformUserFriend {
    private final UUID id;
    private final PlatformUser sender;
    private final PlatformUser receiver;
    private Boolean isConfirmed;
    private final LocalDateTime requestedAt;
    private LocalDateTime confirmedAt;

    public PlatformUserFriend(UUID id,PlatformUser sender, PlatformUser receiver, Boolean isConfirmed, LocalDateTime requestedAt, LocalDateTime confirmedAt) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.isConfirmed = isConfirmed;
        this.requestedAt = requestedAt;
        this.confirmedAt = confirmedAt;
    }

    public static PlatformUserFriend fromDb(
            UUID id,
            PlatformUser sender,
            PlatformUser receiver,
            Boolean isConfirmed,
            LocalDateTime requestedAt,
            LocalDateTime confirmedAt
    ) {
        return new PlatformUserFriend(id,sender, receiver, isConfirmed, requestedAt, confirmedAt);
    }

    public void accept() {
        this.isConfirmed = true;
        this.confirmedAt = LocalDateTime.now();
    }
}
