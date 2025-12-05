package be.kdg.ipj3.platformbackend.user.application;

import be.kdg.ipj3.platformbackend.shared.domain.UserId;
import be.kdg.ipj3.platformbackend.user.application.repository.PlatformUserRepository;
import be.kdg.ipj3.platformbackend.user.domain.PlatformUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FriendService {

    private final PlatformUserRepository platformUserRepository;

    public FriendService(PlatformUserRepository platformUserRepository) {
        this.platformUserRepository = platformUserRepository;
    }

    public PlatformUser findOneWithFriends(UserId userId) {
        log.info("Returning all Games");
        return platformUserRepository.findOneWithFriends(userId);
    }

    public void addFriendToFriendList(UserId userId, UserId friendId) {
        log.info("Adding friend {} to user {}", friendId, userId);
        PlatformUser user = platformUserRepository.findOne(userId);
        PlatformUser friend = platformUserRepository.findOne(friendId);
        user.getFriends().add(friend);
        platformUserRepository.save(user);
    }

    public void removeFriendFromFriendList(UserId userId, UserId friendId) {
        log.info("Removing friend {} from user {}", userId, friendId);
        PlatformUser user = platformUserRepository.findOneWithFriends(userId);
        PlatformUser friend = platformUserRepository.findOne(friendId);
        user.getFriends().remove(friend);
        platformUserRepository.save(user);
    }
}
