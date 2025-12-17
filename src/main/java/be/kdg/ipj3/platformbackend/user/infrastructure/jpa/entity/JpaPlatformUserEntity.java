package be.kdg.ipj3.platformbackend.user.infrastructure.jpa.entity;

import be.kdg.ipj3.platformbackend.shared.domain.UserId;
import be.kdg.ipj3.platformbackend.achievement.domain.UserAchievement;
import be.kdg.ipj3.platformbackend.achievement.infrastructure.jpa.JpaUserAchievementEntity;
import be.kdg.ipj3.platformbackend.user.domain.PlatformUser;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "platform_user")
@Access(AccessType.FIELD)
@Getter
public class JpaPlatformUserEntity {

    @Id
    @Column
    private UUID id;

    @Column
    private String userName;

    @Column
    private String biography;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JpaFriendRequestEntity> sentFriendRequests = new ArrayList<>();

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JpaFriendRequestEntity> receivedFriendRequests = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JpaUserAchievementEntity> achievements = new ArrayList<>();

    @Column
    private String profilePictureUrl;

    @Column
    private String bannerUrl;

    protected JpaPlatformUserEntity() {
    }

    private JpaPlatformUserEntity(UUID id, String userName, String biography, List<JpaFriendRequestEntity> friends, List<JpaUserAchievementEntity> achievements, String profilePictureUrl, String bannerUrl) {
        this.id = id;
        this.userName = userName;
        this.biography = biography;
        this.receivedFriendRequests = friends;
        this.achievements = achievements;
        this.profilePictureUrl = profilePictureUrl;
        this.bannerUrl = bannerUrl;
    }

    public static JpaPlatformUserEntity fromDomain(PlatformUser domain) {
        JpaPlatformUserEntity entity = new JpaPlatformUserEntity();
        entity.id = domain.getUserId().id();
        entity.userName = domain.getUserName();
        entity.biography = domain.getBiography();
        entity.profilePictureUrl = domain.getProfilePictureUrl();
        entity.bannerUrl = domain.getBannerUrl();

        entity.achievements = domain.getAchievements().stream()
                .map(ua -> JpaUserAchievementEntity.fromDomain(ua, entity))
                .collect(Collectors.toList());
        return entity;
    }

    public PlatformUser toDomain() {
        List<UserAchievement> achievementList = achievements.stream()
                .map(JpaUserAchievementEntity::toDomain)
                .collect(Collectors.toList());

        return new PlatformUser(new UserId(id), userName, biography, achievementList, profilePictureUrl, bannerUrl);
    }
}