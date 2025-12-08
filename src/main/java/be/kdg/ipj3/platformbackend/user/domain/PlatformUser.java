package be.kdg.ipj3.platformbackend.user.domain;

import be.kdg.ipj3.platformbackend.achievement.domain.Achievement;
import be.kdg.ipj3.platformbackend.achievement.domain.UserAchievement;
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
    private final List<PlatformUserFriend> friends;
    private List<UserAchievement> achievements;

    public PlatformUser(UserId userId, List<PlatformUserFriend> friends, List<UserAchievement> achievements) {
        this.userId = userId;
        this.friends = friends != null ? friends : new ArrayList<>();
        this.achievements = achievements != null ? achievements : new ArrayList<>();
    }

    public static PlatformUser fromDb(UUID userId, List<PlatformUserFriend> friends, List<UserAchievement> achievements) {
        log.info("Returning user with {} friends: {}", friends != null ? friends.size() : 0, userId);
        List<PlatformUserFriend> friendList = friends != null ? friends : new ArrayList<>();
        return new PlatformUser(new UserId(userId), friendList, achievements);
    }

    public void addAchievement(Achievement achievement){
        achievements.add(new UserAchievement(this.userId,achievement.getId(), LocalDateTime.now()));
    }

}
