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

    public void addFriendToFriendList(UserId userId, UserId friendId) {
        log.info("Adding friend {} to user {}", friendId, userId);
        PlatformUser user = platformUserRepository.findUserById(userId);
        PlatformUserFriend friendRelation = new PlatformUserFriend(
                new PlatformUserFriendId(userId.id(), friendId.id()),
                false,
                LocalDateTime.now(),
                null
        );
        try {
            platformUserRepository.findFriendRequestBetween(friendId.id(), userId);
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
}
