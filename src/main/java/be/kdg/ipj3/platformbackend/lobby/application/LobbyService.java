package be.kdg.ipj3.platformbackend.lobby.application;

import be.kdg.ipj3.platformbackend.game.domain.Game;
import be.kdg.ipj3.platformbackend.game.domain.GameId;
import be.kdg.ipj3.platformbackend.game.domain.repository.GameRepository;
import be.kdg.ipj3.platformbackend.lobby.domain.Lobby;
import be.kdg.ipj3.platformbackend.lobby.domain.LobbyId;
import be.kdg.ipj3.platformbackend.lobby.domain.Player;
import be.kdg.ipj3.platformbackend.lobby.infrastructure.DbLobbyRepository;
import be.kdg.ipj3.platformbackend.shared.domain.UserId;
import be.kdg.ipj3.platformbackend.user.domain.PlatformUser;
import be.kdg.ipj3.platformbackend.user.domain.repository.PlatformUserRepository;
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
    private final GameRepository gameRepository;
    private final PlatformUserRepository platformUserRepository;

    public LobbyService(DbLobbyRepository lobbyRepository, GameRepository gameRepository, PlatformUserRepository platformUserRepository) {
        this.lobbyRepository = lobbyRepository;
        this.gameRepository = gameRepository;
        this.platformUserRepository = platformUserRepository;
    }

    public List<Lobby> findAllLobbies(){
        return lobbyRepository.findAllLobbies();
    }


    public Lobby createNewLobby(UserId userId, int maxPlayerCount, GameId gameId) {
        Game game = gameRepository.findById(gameId.id()).orElseThrow(gameId::notFound);
        Lobby newLobby = new Lobby(new LobbyId(UUID.randomUUID()),game, new ArrayList<>(), LocalDateTime.now(), maxPlayerCount);
        PlatformUser platformUser = platformUserRepository.findUserById(userId).orElseThrow(userId::notFound);
        newLobby.addPlayer(platformUser.getUserId());
        return lobbyRepository.createNewLobby(newLobby);
    }

    public void leaveLobby(UserId userId, LobbyId lobbyId) {
        Lobby lobby = lobbyRepository.findLobbyById(lobbyId).orElseThrow(lobbyId::notFound);
        PlatformUser platformUser = platformUserRepository.findUserById(userId).orElseThrow(userId::notFound);
        Player player = lobby.getPlayers()
                .stream()
                .filter(p -> p.userId().id().equals(userId.id()))
                .findFirst()
                .orElseThrow(() -> new Player(platformUser.getUserId()).notFound());
        lobby.removePlayer(player);
        if(lobby.getPlayers().isEmpty()){
            removeLobby(lobby);
        }else {
            lobbyRepository.save(lobby);
        }
    }
    private void removeLobby(Lobby lobby){
        lobbyRepository.remove(lobby);
    }

    public Lobby addPlayerToLobby(LobbyId lobbyId, UserId userId) {
        Lobby lobby = lobbyRepository.findLobbyById(lobbyId).orElseThrow(lobbyId::notFound);
        PlatformUser platformUser = platformUserRepository.findUserById(userId).orElseThrow(userId::notFound);
        lobby.getPlayers()
                .stream()
                .filter(p -> p.userId().id().equals(userId.id()))
                .findAny()
                .ifPresent(p -> { throw new Player(platformUser.getUserId()).conflict();});
        if(lobby.isLobbyFull()){
            throw lobby.conflict();
        }
        platformUserRepository.findUserById(userId).orElseThrow(userId::notFound);
        lobby.addPlayer(platformUser.getUserId());
        lobbyRepository.save(lobby);
        return lobby;
    }
}
