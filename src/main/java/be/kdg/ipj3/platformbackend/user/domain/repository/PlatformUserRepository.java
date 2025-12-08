package be.kdg.ipj3.platformbackend.user.domain.repository;

import be.kdg.ipj3.platformbackend.shared.domain.UserId;
import be.kdg.ipj3.platformbackend.user.domain.PlatformUser;

import java.util.List;
import java.util.UUID;

public interface PlatformUserRepository {
    PlatformUser findByIdWithFriends(UserId userId);
    PlatformUser findUserById(UserId userId);
    List<PlatformUser> findAllUsersByIds(List<UUID> idList);
    void save(PlatformUser user);
    PlatformUser createUser(PlatformUser user);
}
