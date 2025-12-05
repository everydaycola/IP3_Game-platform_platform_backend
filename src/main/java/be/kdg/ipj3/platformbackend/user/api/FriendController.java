package be.kdg.ipj3.platformbackend.user.api;

import be.kdg.ipj3.platformbackend.shared.domain.UserId;
import be.kdg.ipj3.platformbackend.user.api.dtos.FriendListDto;
import be.kdg.ipj3.platformbackend.user.api.dtos.FriendRequestListDto;
import be.kdg.ipj3.platformbackend.user.application.FriendService;
import be.kdg.ipj3.platformbackend.user.domain.PlatformUserFriend;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<FriendListDto> findAllFriends(@AuthenticationPrincipal Jwt token) {
        UserId userId = UserId.fromToken(token);
        return ResponseEntity.ok(FriendListDto.from(friendService.findUserWithFriends(userId)));
    }

    @PatchMapping("/{friendId}")
    public ResponseEntity<FriendListDto> addFriendRequest(@PathVariable final UUID friendId, @AuthenticationPrincipal Jwt token) {
        UserId userId = UserId.fromToken(token);
        friendService.addFriendRequest(userId,new UserId(friendId));
        return ResponseEntity.ok(FriendListDto.from(friendService.findUserWithFriends(userId)));
    }

    @DeleteMapping("/{friendId}")
    public ResponseEntity<FriendListDto> removeFriend(@PathVariable final UUID friendId,@AuthenticationPrincipal Jwt token) {
        UserId userId = UserId.fromToken(token);
        friendService.removeFriendFromFriendList(userId,new UserId(friendId));
        return ResponseEntity.ok(FriendListDto.from(friendService.findUserWithFriends(userId)));
    }

    @PatchMapping("/{friendId}/accept")
    public ResponseEntity<String> acceptFriendRequest(@PathVariable final UUID friendId, @AuthenticationPrincipal Jwt token) {
        UserId userId = UserId.fromToken(token);
        friendService.acceptFriendRequest(userId, friendId);
        return ResponseEntity.ok("Friend request succesfully accepted!");
    }

    @GetMapping("/requests")
    public ResponseEntity<FriendRequestListDto> findAllFriendRequests(@AuthenticationPrincipal Jwt token) {
        UserId userId = UserId.fromToken(token);
        List<PlatformUserFriend> friendRequests = friendService.findFriendRequestsForUser(userId);
        return ResponseEntity.ok(FriendRequestListDto.from(friendRequests));
    }

}
