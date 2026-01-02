package be.kdg.ipj3.platformbackend.lobby.domain;

import be.kdg.ipj3.platformbackend.game.domain.GameId;
import be.kdg.ipj3.platformbackend.shared.domain.UserId;
import be.kdg.ipj3.platformbackend.shared.domain.exception.ConflictException;
import be.kdg.ipj3.platformbackend.shared.domain.exception.ForbiddenException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Slf4j
public class Lobby {
    LobbyId id;
    GameId gameId;
    UUID currentGameSessionId;
    List<Player> players;
    LocalDateTime creationDate;
    int maxPlayerCount;
    //Todo: correctly map game settings.

    public ConflictException fullConflict(){
        log.error("Lobby is already full {}",id);
        return new ConflictException("Lobby is already full [" + id + "]");
    }
    public ConflictException notFullConflict(){
        log.error("Lobby {} is trying to start with a non full lobby", id);
        return new ConflictException("Lobby is not full yet [" + id +"]");
    }
    public ForbiddenException notLobbyHostForbidden(){
        log.error("Non lobby manager attempted a game start for lobby {}", id);
        return new ForbiddenException("Non lobby manager attempted a game start for lobby ["+id + "]");
    }

    public void addPlayer(UserId userId ){
        this.players.add(new Player(userId));
    }

    public void removePlayer(Player player){
        this.players.remove(player);
    }

    public Player getLobbyManager(){
        return this.players.getFirst();
    }

    public boolean isLobbyFull(){
        return players.size() == maxPlayerCount;
    }

    public void setCurrentGameSession(UUID gameSessionId){
        this.currentGameSessionId = gameSessionId;
    }

}