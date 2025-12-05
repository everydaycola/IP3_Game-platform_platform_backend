package be.kdg.ipj3.platformbackend.user.infrastructure.jpa.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "platform_user_friend")
public class JpaPlatformUserFriendEntity {

    @EmbeddedId
    private JpaPlatformUserFriendId id;

    @ManyToOne
    @MapsId("userId")
    private JpaPlatformUserEntity user;

    @ManyToOne
    @MapsId("friendId")
    private JpaPlatformUserEntity friend;

    private LocalDate reqeuestedAt;
    private Boolean isConfirmed;
    private LocalDate confirmedAt;

    //Todo: impleent to domain and from domain here.

}