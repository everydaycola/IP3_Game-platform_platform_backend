package be.kdg.ipj3.platformbackend.user.infrastructure.jpa.entity;

import be.kdg.ipj3.platformbackend.user.domain.PlatformUserFriend;
import be.kdg.ipj3.platformbackend.user.domain.PlatformUserFriendId;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "platform_user_friend")
@Access(AccessType.FIELD)
public class JpaPlatformUserFriendEntity {

    @EmbeddedId
    private JpaPlatformUserFriendId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private JpaPlatformUserEntity user;

    @ManyToOne
    @MapsId("friendId")
    @JoinColumn(name = "friend_id", nullable = false)
    private JpaPlatformUserEntity friend;

    @Column(name = "requested_at")
    private LocalDateTime requestedAt;

    @Column(name = "is_confirmed")
    private Boolean isConfirmed;

    @Column(name = "confirmed_at")
    private LocalDateTime confirmedAt;

    @Column(name="sender_id")
    private UUID senderId;

    protected JpaPlatformUserFriendEntity() {}

    private JpaPlatformUserFriendEntity(
            JpaPlatformUserFriendId id,
            JpaPlatformUserEntity user,
            JpaPlatformUserEntity friend,
            LocalDateTime requestedAt,
            Boolean isConfirmed,
            LocalDateTime confirmedAt,
            UUID senderId
    ) {
        this.id = id;
        this.user = user;
        this.friend = friend;
        this.requestedAt = requestedAt;
        this.isConfirmed = isConfirmed;
        this.confirmedAt = confirmedAt;
        this.senderId = senderId;
    }

    public static JpaPlatformUserFriendEntity fromDomain(
            PlatformUserFriend domain,
            JpaPlatformUserEntity userEntity,
            JpaPlatformUserEntity friendEntity
    ) {
        JpaPlatformUserFriendId id = new JpaPlatformUserFriendId(
                domain.getId().getUserId(),
                domain.getId().getFriendId()
        );

        return new JpaPlatformUserFriendEntity(
                id,
                userEntity,
                friendEntity,
                domain.getRequestedAt(),
                domain.getIsConfirmed(),
                domain.getConfirmedAt(),
                domain.getSenderId()
        );
    }

    public PlatformUserFriend toDomain() {
        PlatformUserFriendId domainId =PlatformUserFriendId.fromDb(this.id.userId, this.id.friendId);
        return PlatformUserFriend.fromDb(domainId, isConfirmed, requestedAt, confirmedAt, senderId);
    }
}