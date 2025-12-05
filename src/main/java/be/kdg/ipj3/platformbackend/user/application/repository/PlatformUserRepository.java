package be.kdg.ipj3.platformbackend.user.application.repository;

import be.kdg.ipj3.platformbackend.shared.domain.UserId;
import be.kdg.ipj3.platformbackend.user.domain.PlatformUser;


public interface PlatformUserRepository {
    PlatformUser findOneWithFriends(UserId userId);
    PlatformUser findOne(UserId userId);
    void save(PlatformUser user);
    PlatformUser createUser(PlatformUser user);
}
