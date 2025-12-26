package be.kdg.ipj3.platformbackend.lobby.domain;

import java.time.LocalDateTime;

public class Lobby {
    LobbyId id;
    LocalDateTime creationDate;
    int maxPlayerCount;
    //Todo: correctly map game settings.
}
