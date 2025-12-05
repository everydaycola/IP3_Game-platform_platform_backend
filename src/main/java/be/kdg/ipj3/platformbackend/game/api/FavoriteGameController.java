package be.kdg.ipj3.platformbackend.game.api;

import be.kdg.ipj3.platformbackend.game.api.dtos.FullGameDto;
import be.kdg.ipj3.platformbackend.game.application.GameService;
import be.kdg.ipj3.platformbackend.game.domain.Game;
import be.kdg.ipj3.platformbackend.game.domain.GameId;
import be.kdg.ipj3.platformbackend.shared.domain.UserId;
import be.kdg.ipj3.platformbackend.game.application.FavoriteGameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/games/favorite")
public class FavoriteGameController {
    private final GameService gameService;
    private final FavoriteGameService favoriteGameService;

    public FavoriteGameController(GameService gameService, FavoriteGameService favoriteGameService) {
        this.gameService = gameService;
        this.favoriteGameService = favoriteGameService;
    }

    @PostMapping("/{selectedGameId}")
    public ResponseEntity<FullGameDto> addFavorite(@PathVariable final UUID selectedGameId, @AuthenticationPrincipal Jwt token){
        gameService.find(new GameId(selectedGameId));
        UserId userId = UserId.fromToken(token);
        Game favoritedGame = favoriteGameService.addFavoriteGame(new GameId(selectedGameId), userId);
        return ResponseEntity.ok(FullGameDto.from(favoritedGame));
    }

    @DeleteMapping("/{selectedGameId}")
    public ResponseEntity<String> removeFavorite(@PathVariable final UUID selectedGameId, @AuthenticationPrincipal Jwt token){
        gameService.find(new GameId(selectedGameId));
        UserId userId = UserId.fromToken(token);
        favoriteGameService.removeFavoriteGame(new GameId(selectedGameId), userId);
        return ResponseEntity.ok("Favorite game removed successfully.");
    }

}
