package be.kdg.ipj3.platformbackend.user.api.dtos;

import be.kdg.ipj3.platformbackend.user.domain.OwnedCopy;

import java.util.UUID;

public record OwnedCopyDto(UUID id, UUID gameId, boolean isFavorite) {

    public static OwnedCopyDto from(final OwnedCopy ownedCopy) {
        return new OwnedCopyDto(
                ownedCopy.getId().id(),          // assuming OwnedCopyId has an id() method returning UUID
                ownedCopy.getGameId().id(),      // assuming GameId has an id() method returning UUID
                ownedCopy.isFavorite()
        );
    }
}