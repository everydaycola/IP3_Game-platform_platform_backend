package be.kdg.ipj3.platformbackend.lobby.api.dtos;

import be.kdg.ipj3.platformbackend.lobby.domain.Lobby;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record LobbyDto(UUID id,UUID gameId,UUID currentGameSessionId,UUID lobbyHostId, List<PlayerDto> players, LocalDateTime creationDate, int maxPlayers) {
    public static LobbyDto from(final Lobby lobby) {
        return new LobbyDto(
                lobby.getId().id(),
                lobby.getGameId().id(),
                lobby.getCurrentGameSessionId(),
                lobby.getLobbyHost().userId().id(),
                lobby.getPlayers()
                        .stream()
                        .map(PlayerDto::from)
                        .toList(),
                lobby.getCreationDate(),
                lobby.getMaxPlayerCount()
        );
    }
}