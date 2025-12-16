package be.kdg.ipj3.platformbackend.user.infrastructure.jpa.entity;

import be.kdg.ipj3.platformbackend.shared.domain.UserId;
import be.kdg.ipj3.platformbackend.achievement.domain.UserAchievement;
import be.kdg.ipj3.platformbackend.achievement.infrastructure.jpa.JpaUserAchievementEntity;
import be.kdg.ipj3.platformbackend.user.domain.PlatformUser;
import be.kdg.ipj3.platformbackend.user.domain.PlatformFriendRequest;
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

    public static JpaPlatformUserEntity fromDomain(PlatformUser domain) {
        JpaPlatformUserEntity entity = new JpaPlatformUserEntity();
        entity.id = domain.getUserId().id();
        entity.userName = domain.getUserName();
        entity.biography = domain.getBiography();
        entity.profilePictureUrl = domain.getProfilePictureUrl();
        entity.bannerUrl = domain.getBannerUrl();

        entity.receivedFriendRequests = domain.getReceivedFriendRequests().stream()
                .map(friendReq -> JpaFriendRequestEntity
                        .fromDomain(friendReq, JpaPlatformUserEntity.fromDomainWithoutFriends(friendReq.getSender()), JpaPlatformUserEntity.fromDomain(friendReq.getReceiver())
                        ))
                .collect(Collectors.toList());

        entity.sentFriendRequests = domain.getSentFriendRequests().stream()
                .map(friendReq -> JpaFriendRequestEntity
                        .fromDomain(friendReq, JpaPlatformUserEntity.fromDomainWithoutFriends(friendReq.getSender()), JpaPlatformUserEntity.fromDomain(friendReq.getReceiver())
                        ))
                .collect(Collectors.toList());

        entity.achievements = domain.getAchievements().stream()
                .map(ua -> JpaUserAchievementEntity.fromDomain(ua, entity))
                .collect(Collectors.toList());
        return entity;
    }

    public static JpaPlatformUserEntity fromDomainWithoutFriends(PlatformUser domain) {
        JpaPlatformUserEntity entity = new JpaPlatformUserEntity();
        entity.id = domain.getUserId().id();
        entity.userName = domain.getUserName();
        entity.biography = domain.getBiography();
        entity.bannerUrl = domain.getBannerUrl();
        entity.profilePictureUrl = domain.getProfilePictureUrl();
        entity.receivedFriendRequests = new ArrayList<>();
        entity.sentFriendRequests = new ArrayList<>();
        entity.achievements = new ArrayList<>();
        return entity;
    }

    public PlatformUser toDomain() {
        List<PlatformFriendRequest> sentFriendRequests = this.sentFriendRequests.stream()
                .map(JpaFriendRequestEntity::toDomain)
                .collect(Collectors.toList());
        List<PlatformFriendRequest> receivedFriendRequests = this.receivedFriendRequests.stream()
                .map(JpaFriendRequestEntity::toDomain)
                .collect(Collectors.toList());
        List<UserAchievement> achievementList = achievements.stream()
                .map(JpaUserAchievementEntity::toDomain)
                .collect(Collectors.toList());

        return new PlatformUser(new UserId(id), userName, biography, sentFriendRequests, receivedFriendRequests, achievementList, profilePictureUrl, bannerUrl);
    }

    public PlatformUser toDomainWithoutFriendsAndAchievements() {
        return new PlatformUser(new UserId(id), userName, biography, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), profilePictureUrl, bannerUrl);
    }

}