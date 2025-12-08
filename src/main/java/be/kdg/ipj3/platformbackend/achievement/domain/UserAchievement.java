package be.kdg.ipj3.platformbackend.achievement.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class UserAchievement {
    private final UserAchievementId id;
    private final LocalDateTime dateAchieved;
}
