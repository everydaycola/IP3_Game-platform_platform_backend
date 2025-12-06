package be.kdg.ipj3.platformbackend.user.api.dtos;
import be.kdg.ipj3.platformbackend.user.domain.PlatformUser;

import java.util.List;
import java.util.UUID;

public record PlatformUserDto(UUID id) {
    public static PlatformUserDto from(final PlatformUser user){
        return new PlatformUserDto(
                user.getUserId().id()
        );
    }
}
