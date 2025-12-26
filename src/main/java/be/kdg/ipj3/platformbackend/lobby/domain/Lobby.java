package be.kdg.ipj3.platformbackend.lobby.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
public class Lobby {
    LobbyId id;
    List<Player> player;
    LocalDateTime creationDate;
    int maxPlayerCount;
    //Todo: correctly map game settings.
}
