package be.kdg.ipj3.platformbackend.lobby.domain.repository;

import be.kdg.ipj3.platformbackend.lobby.domain.Lobby;
import be.kdg.ipj3.platformbackend.lobby.domain.LobbyId;

import java.util.List;
import java.util.Optional;

public interface LobbyRepository {
    List<Lobby> findAllLobbies();
    Lobby createNewLobby(Lobby newLobby);
    Optional<Lobby> findLobbyById(LobbyId lobbyId);
    void save(Lobby lobby);
    void remove(Lobby lobby);
}
