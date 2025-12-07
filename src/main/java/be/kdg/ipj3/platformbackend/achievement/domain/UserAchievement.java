package be.kdg.ipj3.platformbackend.achievement.domain;

import be.kdg.ipj3.platformbackend.shared.domain.UserId;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@AllArgsConstructor
public class UserAchievement {
    private final UserId userId;
    private final AchievementId achievementId;
    private final LocalDateTime dateAchieved;
}
