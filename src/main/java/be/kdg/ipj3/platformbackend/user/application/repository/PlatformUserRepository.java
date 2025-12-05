package be.kdg.ipj3.platformbackend.user.application.repository;

import be.kdg.ipj3.platformbackend.shared.domain.UserId;
import be.kdg.ipj3.platformbackend.user.domain.PlatformUser;
import be.kdg.ipj3.platformbackend.user.domain.PlatformUserFriend;

import java.util.List;
import java.util.UUID;


public interface PlatformUserRepository {
    PlatformUser findByIdWithFriends(UserId userId);
    PlatformUser findUserById(UserId userId);
    PlatformUserFriend findFriendById(UserId userId,UserId friendId);
    void save(PlatformUser user);
    void save(PlatformUserFriend user);
    PlatformUser createUser(PlatformUser user);
    PlatformUserFriend findFriendRequestBetween(UUID friendId, UserId userId);
    void remove(UserId userId, UserId friendId);
    List<PlatformUserFriend> findAllFriendRequestsForUser(UserId userId);
}
