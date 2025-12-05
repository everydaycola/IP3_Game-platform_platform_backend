package be.kdg.ipj3.platformbackend.user.api;

import be.kdg.ipj3.platformbackend.shared.domain.UserId;
import be.kdg.ipj3.platformbackend.user.api.dtos.FriendListDto;
import be.kdg.ipj3.platformbackend.user.application.FriendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@Slf4j
@RestController
@RequestMapping("/api/user/friends")
public class FriendController {

    private final FriendService friendService;

    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }

    @GetMapping
    public ResponseEntity<FriendListDto> findAll(@AuthenticationPrincipal Jwt token) {
        UserId userId = UserId.fromToken(token);
        return ResponseEntity.ok(FriendListDto.from(friendService.findOneWithFriends(userId)));
    }

    @PatchMapping("/{friendId}")
    public ResponseEntity<FriendListDto> addFriend(@PathVariable final UUID friendId,@AuthenticationPrincipal Jwt token) {
        UserId userId = UserId.fromToken(token);
        friendService.addFriendToFriendList(userId,new UserId(friendId));
        return ResponseEntity.ok(FriendListDto.from(friendService.findOneWithFriends(userId)));
    }

    @DeleteMapping("/{friendId}")
    public ResponseEntity<FriendListDto> removeFriend(@PathVariable final UUID friendId,@AuthenticationPrincipal Jwt token) {
        UserId userId = UserId.fromToken(token);
        friendService.removeFriendFromFriendList(userId,new UserId(friendId));
        return ResponseEntity.ok(FriendListDto.from(friendService.findOneWithFriends(userId)));
    }



}
