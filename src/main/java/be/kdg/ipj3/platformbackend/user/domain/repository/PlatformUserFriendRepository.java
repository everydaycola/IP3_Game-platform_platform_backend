package be.kdg.ipj3.platformbackend.user.domain.repository;

import be.kdg.ipj3.platformbackend.shared.domain.UserId;
import be.kdg.ipj3.platformbackend.user.domain.PlatformUserFriend;

import java.util.List;
import java.util.UUID;


public interface PlatformUserFriendRepository {
    PlatformUserFriend save(PlatformUserFriend user);
    PlatformUserFriend findFriendRequestBetween(UUID friendId, UserId userId);
    void remove(UserId userId, UserId friendId);
    List<PlatformUserFriend> findAllFriendRequestsForUser(UserId userId);
    void validateIfFriendRelationExists(UserId userId, UserId friendId);
    List<UUID> getUniqueFriendIdsForUser(UUID userId);
}
