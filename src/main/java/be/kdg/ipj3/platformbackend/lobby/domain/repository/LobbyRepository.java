package be.kdg.ipj3.platformbackend.lobby.domain.repository;

import be.kdg.ipj3.platformbackend.lobby.domain.Lobby;

import java.util.List;

public interface LobbyRepository {
    List<Lobby> findAllLobbies();
    Lobby createNewLobby(Lobby newLobby);
}
