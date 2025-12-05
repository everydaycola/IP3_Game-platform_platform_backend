package be.kdg.ipj3.platformbackend.user.api.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record FriendDto(
        UUID friendId,
        Boolean isConfirmed,
        LocalDateTime requestedAt,
        LocalDateTime confirmedAt
) {}