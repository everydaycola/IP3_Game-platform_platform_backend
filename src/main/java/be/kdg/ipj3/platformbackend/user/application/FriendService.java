package be.kdg.ipj3.platformbackend.user.application;

import be.kdg.ipj3.platformbackend.shared.domain.UserId;
import be.kdg.ipj3.platformbackend.shared.domain.exception.NotFoundException;
import be.kdg.ipj3.platformbackend.user.application.repository.PlatformUserRepository;
import be.kdg.ipj3.platformbackend.user.domain.PlatformUser;
import be.kdg.ipj3.platformbackend.user.domain.PlatformUserFriend;
import be.kdg.ipj3.platformbackend.user.domain.PlatformUserFriendId;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@Transactional
public class FriendService {

    private final PlatformUserRepository platformUserRepository;

    public FriendService(PlatformUserRepository platformUserRepository) {
        this.platformUserRepository = platformUserRepository;
    }

    public PlatformUser findUserWithFriends(UserId userId) {
        log.info("Returning all Games");
        return platformUserRepository.findByIdWithFriends(userId);
    }

    public void addFriendRequest(UserId userId, UserId friendId) {
        log.info("Adding friend {} to user {}", friendId, userId);
        PlatformUser user = platformUserRepository.findUserById(userId);
        PlatformUser friend = platformUserRepository.findUserById(friendId);

        PlatformUserFriend friendRelation = new PlatformUserFriend(
                new PlatformUserFriendId(userId.id(), friend.getUserId().id()),
                false,
                LocalDateTime.now(),
                null
        );
        try {
            platformUserRepository.findFriendRequestBetween(friend.getUserId().id(), user.getUserId());
            acceptFriendRequest(userId, friendId.id());
            return;
        }catch(NotFoundException e){
            log.info("No existing request between {} and {}", userId, friendId);
        }
        user.getFriends().add(friendRelation);
        platformUserRepository.save(user);
    }

    public void removeFriendFromFriendList(UserId userId, UserId friendId) {
        log.info("Removing friend {} from user {}", userId, friendId);
        platformUserRepository.remove(userId, friendId);
    }

    public void acceptFriendRequest(UserId userId, UUID friendId) {
        log.info("Accepting friend request between {} to user {}", friendId, userId);
        PlatformUserFriend friendRequest = platformUserRepository.findFriendRequestBetween(friendId, userId);
        PlatformUserFriend updatedRequest = new PlatformUserFriend(friendRequest.getId(),true, friendRequest.getRequestedAt(), LocalDateTime.now());
        platformUserRepository.save(updatedRequest);
    }

    public List<PlatformUserFriend> findFriendRequestsForUser(UserId userId) {
        log.info("Finding friend requests for user {}", userId);
        return platformUserRepository.findAllFriendRequestsForUser(userId);
    }
}
