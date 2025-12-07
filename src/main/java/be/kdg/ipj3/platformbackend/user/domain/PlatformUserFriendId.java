package be.kdg.ipj3.platformbackend.user.domain;
import be.kdg.ipj3.platformbackend.shared.domain.exception.ConflictException;
import be.kdg.ipj3.platformbackend.shared.domain.exception.NotFoundException;
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

    public NotFoundException notFound() {
        log.error("No platform friend relation between users {} and {}", userId, friendId);
        return new NotFoundException("No platform friend relation between users ["+userId+"] and [" + friendId +"]");
    }


    public ConflictException conflict() {
        log.error("UserFriend already exists for {} and {}",userId, friendId);
        return new ConflictException("User [" + userId+ "] and ["+friendId + "] already exists.");
    }

}