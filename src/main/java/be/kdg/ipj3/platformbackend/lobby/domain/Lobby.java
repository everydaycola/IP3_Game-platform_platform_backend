package be.kdg.ipj3.platformbackend.lobby.domain;

import be.kdg.ipj3.platformbackend.game.domain.Game;
import be.kdg.ipj3.platformbackend.shared.domain.exception.ConflictException;
import be.kdg.ipj3.platformbackend.user.domain.PlatformUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
@Slf4j
public class Lobby {
    LobbyId id;
    Game game;
    List<Player> players;
    LocalDateTime creationDate;
    int maxPlayerCount;
    //Todo: correctly map game settings.

    public ConflictException conflict(){
        log.error("Lobby is already full {}",id);
        return new ConflictException("Lobby is already full [" + id + "]");
    }

    public void addPlayer(PlatformUser user ){
        this.players.add(new Player(user));
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


}