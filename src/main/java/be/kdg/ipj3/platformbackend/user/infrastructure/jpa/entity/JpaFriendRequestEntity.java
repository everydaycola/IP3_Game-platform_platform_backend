package be.kdg.ipj3.platformbackend.user.infrastructure.jpa.entity;

import be.kdg.ipj3.platformbackend.user.domain.PlatformUserFriend;
import be.kdg.ipj3.platformbackend.user.domain.PlatformUserFriendId;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "platform_user_friend")
public class JpaFriendRequestEntity {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private JpaPlatformUserEntity sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false)
    private JpaPlatformUserEntity receiver;

    @Column(name = "requested_at")
    private LocalDateTime requestedAt;

    @Column(name = "is_confirmed")
    private Boolean isConfirmed;

    @Column(name = "confirmed_at")
    private LocalDateTime confirmedAt;


    protected JpaFriendRequestEntity() {}

    private JpaFriendRequestEntity(
            UUID id,
            JpaPlatformUserEntity sender,
            JpaPlatformUserEntity receiver,
            LocalDateTime requestedAt,
            Boolean isConfirmed,
            LocalDateTime confirmedAt
    ) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.requestedAt = requestedAt;
        this.isConfirmed = isConfirmed;
        this.confirmedAt = confirmedAt;
    }

    public static JpaFriendRequestEntity fromDomain(
            PlatformUserFriend domain,
            JpaPlatformUserEntity sender,
            JpaPlatformUserEntity receiver
    ) {

        return new JpaFriendRequestEntity(
                domain.getId(),
                sender,
                receiver,
                domain.getRequestedAt(),
                domain.getIsConfirmed(),
                domain.getConfirmedAt()
        );
    }

    public PlatformUserFriend toDomain() {
        return PlatformUserFriend.fromDb(this.id, this.sender.toDomainWithoutFriends(), this.receiver.toDomainWithoutFriends(), this.isConfirmed, this.requestedAt, this.confirmedAt);
    }
}