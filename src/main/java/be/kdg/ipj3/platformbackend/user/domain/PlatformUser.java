package be.kdg.ipj3.platformbackend.user.domain;

import be.kdg.ipj3.platformbackend.achievement.domain.AchievementId;
import be.kdg.ipj3.platformbackend.achievement.domain.UserAchievement;
import be.kdg.ipj3.platformbackend.achievement.domain.UserAchievementId;
import be.kdg.ipj3.platformbackend.shared.domain.UserId;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Slf4j
public class PlatformUser {
    private final UserId userId;

    private final String userName;
    private final String biography;
    private List<PlatformFriendRequest> sentFriendRequests = new ArrayList<>();
    private List<PlatformFriendRequest> receivedFriendRequests = new ArrayList<>();
    private List<UserAchievement> achievements = new ArrayList<>();

    public PlatformUser(UserId userId, String userName, String biography, List<PlatformFriendRequest> sentFriendRequests, List<PlatformFriendRequest> receivedFriendRequests, List<UserAchievement> achievements) {
        this.userId = userId;
        this.userName = userName;
        this.biography = biography;
        this.sentFriendRequests = sentFriendRequests != null ? sentFriendRequests : new ArrayList<>();
        this.receivedFriendRequests = receivedFriendRequests != null ? receivedFriendRequests : new ArrayList<>();
        this.achievements = achievements != null ? achievements : new ArrayList<>();
    }

    public List<UserId> getFriendIds() {
        List<UserId> friendIds = new ArrayList<>();

        friendIds.addAll(this.sentFriendRequests.stream()
                .map(friend -> {
                    UUID friendId = friend.getReceiver().getUserId().id();
                    return new UserId(friendId);
                })
                .toList()
        );

        friendIds.addAll(this.receivedFriendRequests.stream()
                .map(friend -> {
                    UUID friendId = friend.getSender().getUserId().id();
                    return new UserId(friendId);
                })
                .toList()
        );
        return friendIds;
    }

    public PlatformUser unlockAchievement(AchievementId achievementId) {
        UserAchievementId newUAId = new UserAchievementId(userId, achievementId);

        if (achievements.stream().anyMatch(userAchievement -> userAchievement.id().equals(newUAId))){
            log.info("User {} already has achievement {}", this.userId.id(), achievementId.id());
            return this;
        }
        achievements.add(new UserAchievement(newUAId, LocalDateTime.now()));
        log.info("User {} has unlocked achievement {}", this.userId.id(), achievementId.id());
        return this;
    }

}
