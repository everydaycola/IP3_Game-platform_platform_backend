package be.kdg.ipj3.platformbackend.user.domain.repository;

import be.kdg.ipj3.platformbackend.shared.domain.UserId;
import be.kdg.ipj3.platformbackend.user.domain.PlatformUser;

public interface PlatformUserRepository {
    PlatformUser findByIdWithFriends(UserId userId);
    PlatformUser findUserById(UserId userId);
    void save(PlatformUser user);
    PlatformUser createUser(PlatformUser user);
}
