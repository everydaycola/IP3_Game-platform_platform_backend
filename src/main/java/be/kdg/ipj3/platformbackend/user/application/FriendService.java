package be.kdg.ipj3.platformbackend.user.application;

import be.kdg.ipj3.platformbackend.shared.domain.UserId;
import be.kdg.ipj3.platformbackend.shared.domain.exception.NotFoundException;
import be.kdg.ipj3.platformbackend.user.domain.repository.PlatformUserFriendRepository;
import be.kdg.ipj3.platformbackend.user.domain.repository.PlatformUserRepository;
import be.kdg.ipj3.platformbackend.user.domain.PlatformUser;
import be.kdg.ipj3.platformbackend.user.domain.PlatformUserFriend;
import be.kdg.ipj3.platformbackend.user.domain.PlatformUserFriendId;
import be.kdg.ipj3.platformbackend.user.infrastructure.jpa.entity.JpaPlatformUserEntity;
import be.kdg.ipj3.platformbackend.user.infrastructure.jpa.entity.JpaPlatformUserFriendId;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@Transactional
public class FriendService {

    private final PlatformUserRepository platformUserRepository;
    private final PlatformUserFriendRepository platformUserFriendRepository;

    public FriendService(PlatformUserRepository platformUserRepository, PlatformUserFriendRepository platformUserFriendRepository) {
        this.platformUserRepository = platformUserRepository;
        this.platformUserFriendRepository = platformUserFriendRepository;
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
                UUID.randomUUID(),
                user,
                friend,
                false,
                LocalDateTime.now(),
                null
        );
        try {
            platformUserFriendRepository.findFriendRequestBetween(friend.getUserId().id(), user.getUserId());
            acceptFriendRequest(userId, friendId.id());
            return;
        }catch(NotFoundException e){
            log.info("No existing request between {} and {}", userId, friendId);
        }
        platformUserFriendRepository.validateIfFriendRelationExists(userId, friendId);
        user.getFriends().add(friendRelation);
        platformUserRepository.save(user);
    }

    public void removeFriendFromFriendList(UserId userId, UserId friendId) {
        log.info("Removing friend {} from user {}", userId, friendId);
        platformUserFriendRepository.remove(userId, friendId);
    }

    public PlatformUserFriend acceptFriendRequest(UserId userId, UUID friendId) {
        log.info("Accepting friend request between {} to user {}", friendId, userId);
        PlatformUserFriend friendRequest = platformUserFriendRepository.findFriendRequestBetween(friendId, userId);
        friendRequest.accept();
        return platformUserFriendRepository.save(friendRequest);
    }

    public void denyFriendRequest(UserId userId, UUID friendId) {
        log.info("Denying friend request between {} to user {}", friendId, userId);
        platformUserFriendRepository.remove(userId, new UserId(friendId));
    }

    public List<PlatformUserFriend> findFriendRequestsForUser(UserId userId) {
        log.info("Finding friend requests for user {}", userId);
        return platformUserFriendRepository.findAllFriendRequestsForUser(userId);
    }

    public List<PlatformUser> getFriendRecommendations(UUID id, int size, String nameQuery) {
        log.info("Finding friend recommendations for user");
        List<UUID> friendIds = platformUserFriendRepository.getUniqueFriendIdsForUser(id);
        friendIds.add(id);
        List<PlatformUser> recommendations  = new ArrayList<>();
        if(nameQuery != null && !nameQuery.isEmpty()){
            recommendations.addAll(
                    platformUserRepository.findRecommendationsListOfSizeWithNameQuery(friendIds,nameQuery, size)
                            .stream()
                            .map(JpaPlatformUserEntity::toDomain)
                            .toList()
            );
        }
        int remaining = size - recommendations.size();
        if(remaining > 0){
            recommendations.forEach(recommendation -> friendIds.add(recommendation.getUserId().id()));
            recommendations.addAll(
                    platformUserRepository.findRecommendationsListOfSize(friendIds, size)
                            .stream()
                            .map(JpaPlatformUserEntity::toDomain)
                            .toList()
            );
        }
        return recommendations;
    }
}
