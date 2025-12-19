package be.kdg.ipj3.platformbackend.user.api;

import be.kdg.ipj3.platformbackend.user.api.dtos.FavoriteGameDto;
import be.kdg.ipj3.platformbackend.game.api.dtos.FullGameDto;
import be.kdg.ipj3.platformbackend.game.domain.Game;
import be.kdg.ipj3.platformbackend.game.domain.GameId;
import be.kdg.ipj3.platformbackend.shared.domain.UserId;
import be.kdg.ipj3.platformbackend.user.api.dtos.PlatformUserDto;
import be.kdg.ipj3.platformbackend.user.api.dtos.UpdateUserProfileRequestDto;
import be.kdg.ipj3.platformbackend.user.application.UserService;
import be.kdg.ipj3.platformbackend.user.domain.OwnedCopy;
import be.kdg.ipj3.platformbackend.user.helpers.JwtHelpers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<PlatformUserDto> getUserData(@AuthenticationPrincipal Jwt token) {
        UserId userId = UserId.fromToken(token);
        log.info("User with id {} was recognized by the platform", userId);
        return ResponseEntity.ok(PlatformUserDto.from(userService.findUserById(userId)));
    }

    @PostMapping
    public ResponseEntity<PlatformUserDto> addUser(@AuthenticationPrincipal Jwt token) {
        UserId userId = UserId.fromToken(token);
        String userName = JwtHelpers.userNameFromToken(token);
        log.info("User with id {} is requesting to be added to the platform", userId);
        return ResponseEntity.ok(PlatformUserDto.from(userService.findOrCreateUserById(userId, userName)));
    }

    @PatchMapping
    public ResponseEntity<PlatformUserDto> updateUser(
            @AuthenticationPrincipal Jwt token,
            @RequestBody UpdateUserProfileRequestDto request
    ) {
        UserId userId = UserId.fromToken(token);
        log.info("User with id {} is updating their profile", userId);
        return ResponseEntity.ok(PlatformUserDto.from(userService.updateUserProfile(userId, request)));
    }

    @GetMapping("/favorites")
    public ResponseEntity<List<FavoriteGameDto>> getAllFavorite(@AuthenticationPrincipal Jwt token) {
        UserId userId = UserId.fromToken(token);
        List<OwnedCopy> favorites = userService.findAllFavoriteGames(userId);
        List<FavoriteGameDto> dtos = favorites.stream()
                .map(ownedCopy -> new FavoriteGameDto(userId.id(), ownedCopy.getGameId().id()))
                .toList();

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/favorites/{gameUUId}")
    public ResponseEntity<FavoriteGameDto> getFavorite(@PathVariable final UUID gameUUId, @AuthenticationPrincipal Jwt token) {
        UserId userId = UserId.fromToken(token);
        GameId gameId = new GameId(gameUUId);
        OwnedCopy favorite = userService.findFavoriteGameByGameId(userId, gameId);
        return ResponseEntity.ok(new FavoriteGameDto(userId.id(), favorite.getGameId().id()));
    }


    @PostMapping("/favorites/{selectedGameId}")
    public ResponseEntity<FullGameDto> addFavorite(@PathVariable final UUID selectedGameId, @AuthenticationPrincipal Jwt token) {
        UserId userId = UserId.fromToken(token);
        GameId gameId = new GameId(selectedGameId);
        Game favoritedGame = userService.addFavoriteGame(userId, gameId);
        return ResponseEntity.ok(FullGameDto.from(favoritedGame));
    }

    @DeleteMapping("/favorites/{selectedGameId}")
    public ResponseEntity<String> removeFavorite(@PathVariable final UUID selectedGameId, @AuthenticationPrincipal Jwt token) {
        UserId userId = UserId.fromToken(token);
        GameId gameId = new GameId(selectedGameId);
        userService.removeFavoriteGame(userId, gameId);
        return ResponseEntity.ok("Favorite game removed successfully.");
    }
}
