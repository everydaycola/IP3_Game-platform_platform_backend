package be.kdg.ipj3.platformbackend.lobby.application;

import be.kdg.ipj3.platformbackend.game.domain.Game;
import be.kdg.ipj3.platformbackend.game.domain.GameId;
import be.kdg.ipj3.platformbackend.game.domain.repository.GameRepository;
import be.kdg.ipj3.platformbackend.lobby.api.dtos.request.StartGameRequest;
import be.kdg.ipj3.platformbackend.lobby.domain.Lobby;
import be.kdg.ipj3.platformbackend.lobby.domain.LobbyId;
import be.kdg.ipj3.platformbackend.lobby.domain.Player;
import be.kdg.ipj3.platformbackend.lobby.infrastructure.DbLobbyRepository;
import be.kdg.ipj3.platformbackend.shared.domain.UserId;
import be.kdg.ipj3.platformbackend.user.domain.PlatformUser;
import be.kdg.ipj3.platformbackend.user.domain.repository.PlatformUserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

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
    private final RestClient restClient;

    public LobbyService(DbLobbyRepository lobbyRepository, GameRepository gameRepository, PlatformUserRepository platformUserRepository, RestClient.Builder restClientBuilder) {
        this.lobbyRepository = lobbyRepository;
        this.gameRepository = gameRepository;
        this.platformUserRepository = platformUserRepository;
        this.restClient = restClientBuilder.build();
    }

    public List<Lobby> findAllLobbies() {
        return lobbyRepository.findAllLobbies();
    }


    public Lobby createNewLobby(UserId userId, int maxPlayerCount, GameId gameId) {
        Game game = gameRepository.findById(gameId.id()).orElseThrow(gameId::notFound);
        Lobby newLobby = new Lobby(new LobbyId(UUID.randomUUID()), gameId,null, new ArrayList<>(), LocalDateTime.now(), maxPlayerCount);
        PlatformUser platformUser = platformUserRepository.findUserById(userId).orElseThrow(userId::notFound);
        newLobby.addPlayer(platformUser.getUserId());
        return lobbyRepository.createNewLobby(newLobby, game);
    }

    public void leaveLobby(UserId userId, LobbyId lobbyId) {
        Lobby lobby = lobbyRepository.findLobbyById(lobbyId).orElseThrow(lobbyId::notFound);
        Game game = gameRepository.findById(lobby.getGameId().id()).orElseThrow(lobby.getGameId()::notFound);
        PlatformUser platformUser = platformUserRepository.findUserById(userId).orElseThrow(userId::notFound);
        Player player = lobby.getPlayers()
                .stream()
                .filter(p -> p.userId().id().equals(userId.id()))
                .findFirst()
                .orElseThrow(() -> new Player(platformUser.getUserId()).notFound());
        lobby.removePlayer(player);
        if (lobby.getPlayers().isEmpty()) {
            removeLobby(lobby, game);
        } else {
            lobbyRepository.save(lobby, game);
        }
    }

    private void removeLobby(Lobby lobby, Game game) {
        lobbyRepository.remove(lobby, game);
    }

    public Lobby addPlayerToLobby(LobbyId lobbyId, UserId userId) {
        Lobby lobby = lobbyRepository.findLobbyById(lobbyId).orElseThrow(lobbyId::notFound);
        Game game = gameRepository.findById(lobby.getGameId().id()).orElseThrow(lobby.getGameId()::notFound);
        PlatformUser platformUser = platformUserRepository.findUserById(userId).orElseThrow(userId::notFound);
        lobby.getPlayers()
                .stream()
                .filter(p -> p.userId().id().equals(userId.id()))
                .findAny()
                .ifPresent(p -> {
                    throw new Player(platformUser.getUserId()).conflict();
                });
        if (lobby.isLobbyFull()) {
            throw lobby.conflict();
        }
        platformUserRepository.findUserById(userId).orElseThrow(userId::notFound);
        lobby.addPlayer(platformUser.getUserId());
        lobbyRepository.save(lobby,game);
        return lobby;
    }

    public Lobby findLobby(LobbyId lobbyId) {
        return lobbyRepository.findLobbyById(lobbyId).orElseThrow(lobbyId::notFound);
    }

    //Todo: validate if the lobby is actualy full.
    public UUID startGame(UserId player1Id,UserId player2Id, LobbyId lobbyId, Jwt token) {
        PlatformUser user1 = platformUserRepository.findUserById(player1Id).orElseThrow(player1Id::notFound);
        PlatformUser user2 = platformUserRepository.findUserById(player2Id).orElseThrow(player2Id::notFound);
        Lobby lobby = lobbyRepository.findLobbyById(lobbyId)
                .orElseThrow(lobbyId::notFound);
        Game game = gameRepository.findById(lobby.getGameId().id())
                .orElseThrow(lobby.getGameId()::notFound);
        try {
            JsonNode responseBody = restClient.post()
                    .uri(game.getGameStartEndpoint())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token.getTokenValue())
                    .body(new StartGameRequest(user1.getUserId().id(), user2.getUserId().id()))
                    .retrieve()
                    .body(JsonNode.class);
            if (responseBody == null || !responseBody.has("id")) {
                throw new IllegalStateException(
                        "Game started but no 'id' field returned from " + game.getGameStartEndpoint()
                );
            }
            UUID gameSessionId = UUID.fromString(responseBody.get("id").asText());
            lobby.setCurrentGameSession(gameSessionId);
            lobbyRepository.save(lobby,game);
            log.info("Started game {} on server {}. Set Session ID: {} in lobby {}",
                    game.getName(), game.getGameStartEndpoint(), gameSessionId, lobby.getId());

            return gameSessionId;
        } catch (Exception e) {
            log.error("Failed to start game on remote server", e);
            throw new RuntimeException("Could not start game session", e);
        }
    }
}
