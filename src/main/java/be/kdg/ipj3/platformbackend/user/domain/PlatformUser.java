package be.kdg.ipj3.platformbackend.user.domain;

import be.kdg.ipj3.platformbackend.shared.domain.UserId;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Slf4j
public class PlatformUser {
    private final UserId userId;
    private final List<PlatformUserFriend> friends;

    public PlatformUser(UserId userId, List<PlatformUserFriend> friends) {
        this.userId = userId;
        this.friends = friends != null ? friends : new ArrayList<>();
    }

    public static PlatformUser fromDb(UUID userId, List<PlatformUserFriend> friends) {
        log.info("Returning user with {} friends: {}", friends != null ? friends.size() : 0, userId);
        List<PlatformUserFriend> friendList = friends != null ? friends : new ArrayList<>();
        return new PlatformUser(new UserId(userId), friendList);
    }
}
