package be.kdg.ipj3.platformbackend.lobby.api;

import be.kdg.ipj3.platformbackend.lobby.api.dtos.LobbyDto;
import be.kdg.ipj3.platformbackend.lobby.application.LobbyService;
import be.kdg.ipj3.platformbackend.shared.domain.UserId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/lobby")
public class LobbyController {

    private final LobbyService lobbyService;

    public LobbyController(LobbyService lobbyService) {
        this.lobbyService = lobbyService;
    }

    @GetMapping
    public ResponseEntity<List<LobbyDto>> findAllLobbies() {
        List<LobbyDto> lobbyDtos = lobbyService.findAllLobbies().stream().map(LobbyDto::from).toList();
        return ResponseEntity.ok(lobbyDtos);
    }

    @PostMapping
    public ResponseEntity<LobbyDto> createNewLobby(@AuthenticationPrincipal Jwt token){
        UserId userId = UserId.fromToken(token);
        return ResponseEntity.ok(LobbyDto.from(lobbyService.createNewLobby(userId,2)));
    }

}