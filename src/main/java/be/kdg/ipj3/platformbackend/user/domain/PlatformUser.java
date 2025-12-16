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
    private List<UserAchievement> achievements = new ArrayList<>();
    private final String profilePictureUrl;
    private final String bannerUrl;

    public PlatformUser(UserId userId, String userName, String biography, List<UserAchievement> achievements, String profilePictureUrl, String bannerUrl) {
        this.userId = userId;
        this.userName = userName;
        this.biography = biography;
        this.achievements = achievements != null ? achievements : new ArrayList<>();
        this.profilePictureUrl = profilePictureUrl;
        this.bannerUrl = bannerUrl;
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
