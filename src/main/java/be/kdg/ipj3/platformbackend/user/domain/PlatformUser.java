package be.kdg.ipj3.platformbackend.user.domain;
import be.kdg.ipj3.platformbackend.shared.domain.UserId;
import be.kdg.ipj3.platformbackend.user.infrastructure.jpa.entity.JpaPlatformUserEntity;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Slf4j
public class PlatformUser {
    UserId userId;
    List<PlatformUser> friends;

    public PlatformUser(UserId userId, List<PlatformUser> friends) {
        this.userId = userId;
        this.friends = friends;
    }

    public static PlatformUser fromDbWithoutFriends(UUID userId) {
        log.info("returning user without friends: {}", userId);
        return new PlatformUser(new UserId(userId), new ArrayList<PlatformUser>());
    }

    public static PlatformUser fromDb(UUID userId, List<JpaPlatformUserEntity> friendEntities) {
        log.info("returning user with {} friends: {}", friendEntities != null ? friendEntities.size() : 0, userId);

        List<PlatformUser> friends = new ArrayList<>();
        if (friendEntities != null && !friendEntities.isEmpty()) {
            friends = friendEntities.stream()
                    .map(JpaPlatformUserEntity::toDomainWithoutFriends)
                    .collect(Collectors.toList());
        }

        return new PlatformUser(new UserId(userId), friends);
    }
}
