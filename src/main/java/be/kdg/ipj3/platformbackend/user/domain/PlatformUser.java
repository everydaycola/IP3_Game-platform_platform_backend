package be.kdg.ipj3.platformbackend.user.domain;

import be.kdg.ipj3.platformbackend.achievement.domain.Achievement;
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
    private final List<PlatformUserFriend> friends;
    private List<UserAchievement> achievements;

    public PlatformUser(UserId userId, String userName, String biography, List<PlatformUserFriend> friends, List<UserAchievement> achievements) {
        this.userId = userId;
        this.userName = userName;
        this.biography = biography;
        this.friends = friends != null ? friends : new ArrayList<>();
        this.achievements = achievements != null ? achievements : new ArrayList<>();
    }

    public static PlatformUser fromDb(UUID userId, List<PlatformUserFriend> friends, List<UserAchievement> achievements) {
        log.info("Returning user with {} friends: {}", friends != null ? friends.size() : 0, userId);
        List<PlatformUserFriend> friendList = friends != null ? friends : new ArrayList<>();
        return new PlatformUser(new UserId(userId),"", "", friendList,achievements );
    }
    public static PlatformUser fromDb(UUID userId,String userName, String biography, List<PlatformUserFriend> friends, List<UserAchievement> achievements) {
        log.info("Returning user with {} friends: {}", friends != null ? friends.size() : 0, userId);
        List<PlatformUserFriend> friendList = friends != null ? friends : new ArrayList<>();
        return new PlatformUser(new UserId(userId),userName, biography, friendList, achievements);
    }

    public void gainAchievement(Achievement achievement) {
        achievements.add(new UserAchievement(new UserAchievementId(userId, achievement.getId()), LocalDateTime.now()));
    }

}
