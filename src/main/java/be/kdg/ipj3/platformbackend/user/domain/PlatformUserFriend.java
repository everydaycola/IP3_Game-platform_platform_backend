package be.kdg.ipj3.platformbackend.user.domain;

import be.kdg.ipj3.platformbackend.user.infrastructure.jpa.entity.JpaPlatformUserFriendEntity;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Slf4j
public class PlatformUserFriend {

    private final PlatformUserFriendId id;
    private final Boolean isConfirmed;
    private final LocalDate reqeuestedAt;
    private final LocalDate confirmedAt;

    public PlatformUserFriend(UUID friendId, PlatformUserFriendId id, Boolean isConfirmed, LocalDate reqeuestedAt, LocalDate confirmedAt) {
        this.id = id;
        this.isConfirmed = isConfirmed;
        this.reqeuestedAt = reqeuestedAt;
        this.confirmedAt = confirmedAt;
    }
    //Todo: implement the correct fromJpa here.
    public static PlatformUserFriend fromJpa(JpaPlatformUserFriendEntity  entity) {
        return null;
    }
}
