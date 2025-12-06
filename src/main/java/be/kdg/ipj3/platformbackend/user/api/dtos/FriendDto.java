package be.kdg.ipj3.platformbackend.user.api.dtos;

import be.kdg.ipj3.platformbackend.user.domain.PlatformUserFriend;

import java.time.LocalDateTime;
import java.util.UUID;

public record FriendDto(UUID user1, UUID user2, Boolean isConfirmed, LocalDateTime requestedAt, LocalDateTime confirmedAt) {
    public static FriendDto from(final PlatformUserFriend friend) {
        return new FriendDto(
                friend.getId().getUserId(),
                friend.getId().getFriendId(),
                friend.getIsConfirmed(),
                friend.getRequestedAt(),
                friend.getConfirmedAt()
        );
    }
}