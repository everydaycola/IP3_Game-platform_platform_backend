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

    public static PlatformUser fromDbWithoutFriends(UUID userId) {
        log.info("Returning user without friends: {}", userId);
        return new PlatformUser(new UserId(userId), new ArrayList<>());
    }

    public static PlatformUser fromDb(UUID userId, List<be.kdg.ipj3.platformbackend.user.infrastructure.jpa.entity.JpaPlatformUserFriendEntity> friendEntities) {
        log.info("Returning user with {} friends: {}", friendEntities != null ? friendEntities.size() : 0, userId);

        List<PlatformUserFriend> friends = new ArrayList<>();
        if (friendEntities != null && !friendEntities.isEmpty()) {
            friends = friendEntities.stream()
                    .map(PlatformUserFriend::fromJpa) // convert each join entity to domain
                    .toList();
        }

        return new PlatformUser(new UserId(userId), friends);
    }
}
