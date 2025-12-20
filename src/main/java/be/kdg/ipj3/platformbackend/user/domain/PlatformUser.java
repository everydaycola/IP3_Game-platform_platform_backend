package be.kdg.ipj3.platformbackend.user.domain;

import be.kdg.ipj3.platformbackend.achievement.domain.AchievementId;
import be.kdg.ipj3.platformbackend.achievement.domain.UserAchievement;
import be.kdg.ipj3.platformbackend.achievement.domain.UserAchievementId;
import be.kdg.ipj3.platformbackend.game.domain.GameId;
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
    private String biography;
    private List<UserAchievement> achievements = new ArrayList<>();
    private String profilePictureUrl;
    private String bannerUrl;
    private double credits;

    private final List<OwnedCopy> ownedGames;

    public PlatformUser(UserId userId, String userName, String biography, List<UserAchievement> achievements, String profilePictureUrl, String bannerUrl, double credits, List<OwnedCopy> ownedGames) {
        this.userId = userId;
        this.userName = userName;
        this.biography = biography;
        this.achievements = achievements != null ? achievements : new ArrayList<>();
        this.profilePictureUrl = profilePictureUrl;
        this.bannerUrl = bannerUrl;
        this.credits = credits;
        this.ownedGames = ownedGames;
    }

    public PlatformUser unlockAchievement(AchievementId achievementId) {
        UserAchievementId newUAId = new UserAchievementId(userId, achievementId);

        if (achievements.stream().anyMatch(userAchievement -> userAchievement.id().equals(newUAId))) {
            log.info("User {} already has achievement {}", this.userId.id(), achievementId.id());
            return this;
        }
        achievements.add(new UserAchievement(newUAId, LocalDateTime.now()));
        log.info("User {} has unlocked achievement {}", this.userId.id(), achievementId.id());
        return this;
    }

    public void updateProfileDetails(String biography, String profilePictureUrl, String bannerUrl) {
        this.biography = biography;
        this.profilePictureUrl = profilePictureUrl;
        this.bannerUrl = bannerUrl;
    }

    public void addCredits(double credits) {
        this.credits += credits;
    }

    public void removeCredits(double credits) {
        this.credits -= credits;
    }

    public OwnedCopy buyGame(GameId gameId, double price) {
        removeCredits(price);
        return addOwnedGame(gameId);
    }

    public OwnedCopy addOwnedGame(GameId gameId) {
        OwnedCopy oc = new OwnedCopy(new OwnedCopyId(UUID.randomUUID()), gameId, false);
        this.ownedGames.add(oc);
        return oc;
    }

    public void favoriteGame(OwnedCopyId ocId, boolean favorite) {
        this.ownedGames.stream()
                .filter(oc -> oc.getId().id().equals(ocId.id()))
                .findFirst().orElseThrow(ocId::notFound).setFavorite(favorite);
    }
}
