package be.kdg.ipj3.platformbackend.user.api.dtos;
import be.kdg.ipj3.platformbackend.user.domain.PlatformUser;

import java.util.List;
import java.util.UUID;

public record FriendListDto(UUID id, List<PlatformUser> friends) {
    public static FriendListDto from(final PlatformUser user){
        return new FriendListDto(
                user.getUserId().id(),
                user.getFriends()
        );
    }
}
