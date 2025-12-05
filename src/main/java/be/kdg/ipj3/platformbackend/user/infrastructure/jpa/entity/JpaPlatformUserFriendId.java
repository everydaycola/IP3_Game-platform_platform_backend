package be.kdg.ipj3.platformbackend.user.infrastructure.jpa.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class JpaPlatformUserFriendId implements Serializable {
    private UUID userId;
    private UUID friendId;
}
