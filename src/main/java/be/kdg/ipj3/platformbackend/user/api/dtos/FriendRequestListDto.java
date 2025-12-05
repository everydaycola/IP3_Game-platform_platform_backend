package be.kdg.ipj3.platformbackend.user.api.dtos;
import be.kdg.ipj3.platformbackend.user.domain.PlatformUserFriend;

import java.util.List;
import java.util.stream.Collectors;

public record FriendRequestListDto(List<FriendDto> friends) {
    public static FriendRequestListDto from(final List<PlatformUserFriend> friends) {
        List<FriendDto> friendDtos = friends
                .stream()
                .map(f -> new FriendDto(
                        f.getId().getFriendId(),
                        f.getIsConfirmed(),
                        f.getRequestedAt(),
                        f.getConfirmedAt()
                ))
                .collect(Collectors.toList());

        return new FriendRequestListDto(
                friendDtos
        );
    }
}