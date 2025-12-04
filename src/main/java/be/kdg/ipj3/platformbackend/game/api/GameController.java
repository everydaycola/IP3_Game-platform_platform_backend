package be.kdg.ipj3.platformbackend.game.api;

import be.kdg.ipj3.platformbackend.game.api.dtos.FullGameDto;
import be.kdg.ipj3.platformbackend.game.api.dtos.GameListdto;
import be.kdg.ipj3.platformbackend.game.application.GameService;
import be.kdg.ipj3.platformbackend.game.domain.Game;
import be.kdg.ipj3.platformbackend.game.domain.GameId;
import be.kdg.ipj3.platformbackend.shared.domain.UserId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/games")
public class GameController {
    private final GameService games;

    public GameController(GameService games) {
        this.games = games;
    }

    @GetMapping
    public ResponseEntity<List<GameListdto>> findAll(){
        List<GameListdto> dtos = games.findAll().stream().map(GameListdto::from).toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FullGameDto> findGame(@PathVariable final UUID id){
        Game game = games.find(new GameId(id));
        return ResponseEntity.ok(FullGameDto.from(game));
    }

    @PostMapping("/favorite/{selectedGameId}")
    public ResponseEntity<FullGameDto> addFavorite(@PathVariable final UUID selectedGameId, @AuthenticationPrincipal Jwt token){
        games.find(new GameId(selectedGameId));
        UserId userId = UserId.fromToken(token);
        Game favoritedGame = games.addFavoriteGame(new GameId(selectedGameId), userId);
        return ResponseEntity.ok(FullGameDto.from(favoritedGame));
    }

    @DeleteMapping("/favorite/{selectedGameId}")
    public ResponseEntity<String> removeFavorite(@PathVariable final UUID selectedGameId, @AuthenticationPrincipal Jwt token){
        games.find(new GameId(selectedGameId));
        UserId userId = UserId.fromToken(token);
        games.removeFavoriteGame(new GameId(selectedGameId), userId);
        return ResponseEntity.ok("Favorite game removed successfully.");
    }

}
