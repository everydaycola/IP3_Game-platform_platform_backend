package be.kdg.ipj3.platformbackend.lobby.domain;
import be.kdg.ipj3.platformbackend.shared.domain.UserId;
import be.kdg.ipj3.platformbackend.shared.domain.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public record Player(UserId userId, LobbyId lobbyId) {

    public NotFoundException notFound() {
        log.error("No relation between user {} and lobby {}", userId.id(), lobbyId.id());
        return new NotFoundException("No relation between user [" + userId.id() + "] and lobby [" + lobbyId.id() + "]");
    }

}
