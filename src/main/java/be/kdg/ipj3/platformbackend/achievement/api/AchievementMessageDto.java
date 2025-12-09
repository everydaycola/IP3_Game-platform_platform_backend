package be.kdg.ipj3.platformbackend.achievement.api;

import java.util.UUID;

public record AchievementMessageDto(UUID userId, UUID achievementId) {
}
