package be.kdg.ipj3.platformbackend.game.api;

import be.kdg.ipj3.platformbackend.game.api.dtos.FavoriteGameDto;
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

import java.util.List;
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

    @GetMapping
    public ResponseEntity<List<FavoriteGameDto>> getAllFavorite(@AuthenticationPrincipal Jwt token){
        UserId userId = UserId.fromToken(token);
        List<FavoriteGame> favorites =  favoriteGameService.findAll(userId);
        List<FavoriteGameDto> dtos = favorites.stream()
                .map(FavoriteGameDto::from)
                .toList();

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<FavoriteGameDto> getFavorite(@PathVariable final UUID gameId,@AuthenticationPrincipal Jwt token){
        UserId userId = UserId.fromToken(token);
        FavoriteGame favorite = favoriteGameService.findFavoriteGame(userId, gameId);
        return ResponseEntity.ok(FavoriteGameDto.from(favorite));
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
