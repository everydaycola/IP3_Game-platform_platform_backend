package be.kdg.ipj3.platformbackend.lobby.application;

import be.kdg.ipj3.platformbackend.lobby.domain.Lobby;
import be.kdg.ipj3.platformbackend.lobby.domain.LobbyId;
import be.kdg.ipj3.platformbackend.lobby.domain.Player;
import be.kdg.ipj3.platformbackend.lobby.infrastructure.DbLobbyRepository;
import be.kdg.ipj3.platformbackend.shared.domain.UserId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@Transactional
public class LobbyService {
    private final DbLobbyRepository lobbyRepository;

    public LobbyService(DbLobbyRepository lobbyRepository) {
        this.lobbyRepository = lobbyRepository;
    }

    public List<Lobby> findAllLobbies(){
        return lobbyRepository.findAllLobbies();
    }

    public Lobby createNewLobby(UserId userId, int maxPlayerCount) {
        Lobby newLobby = new Lobby(new LobbyId(UUID.randomUUID()),new ArrayList<>(), LocalDateTime.now(), maxPlayerCount);
        newLobby.getPlayers().add(new Player(userId, newLobby.getId()));
        return lobbyRepository.createNewLobby(newLobby);
    }
}
