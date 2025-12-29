package be.kdg.ipj3.platformbackend.lobby.domain;

import be.kdg.ipj3.platformbackend.shared.domain.UserId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
public class Lobby {
    LobbyId id;
    List<Player> players;
    LocalDateTime creationDate;
    int maxPlayerCount;
    //Todo: correctly map game settings.

    public void addPlayer(UserId userId ){
        this.players.add(new Player(userId, this.id));
    }

}