package be.kdg.ipj3.platformbackend.user.api;

import be.kdg.ipj3.platformbackend.shared.domain.UserId;
import be.kdg.ipj3.platformbackend.user.api.dtos.FriendDto;
import be.kdg.ipj3.platformbackend.user.api.dtos.FriendListDto;
import be.kdg.ipj3.platformbackend.user.api.dtos.FriendRequestListDto;
import be.kdg.ipj3.platformbackend.user.application.FriendService;
import be.kdg.ipj3.platformbackend.user.application.UserService;
import be.kdg.ipj3.platformbackend.user.domain.PlatformUser;
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
    private final UserService userService;

    public FriendController(FriendService friendService, UserService userService) {
        this.friendService = friendService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<FriendListDto> findAllFriends(@AuthenticationPrincipal Jwt token) {
        UserId userId = UserId.fromToken(token);
        PlatformUser user = friendService.findUserWithFriends(userId);
        List<UserId> friendIds = user.getFriends().stream()
                .map(friend -> {
                    UUID friendId = friend.getId().getUserId().equals(userId.id())
                            ? friend.getId().getFriendId()
                            : friend.getId().getUserId();
                    return new UserId(friendId);
                })
                .toList();
        List<PlatformUser> fullFriends =  userService.findUserListByIdList(friendIds);
        return ResponseEntity.ok(FriendListDto.from(user, fullFriends));
    }

    @PatchMapping("/{friendId}")
    public ResponseEntity<FriendListDto> addFriendRequest(@PathVariable final UUID friendId, @AuthenticationPrincipal Jwt token) {
        UserId userId = UserId.fromToken(token);
        friendService.addFriendRequest(userId, new UserId(friendId));

        PlatformUser user = friendService.findUserWithFriends(userId);
        List<UserId> friendIds = extractFriendIds(user.getFriends(), userId);
        List<PlatformUser> fullFriends = userService.findUserListByIdList(friendIds);

        return ResponseEntity.ok(FriendListDto.from(user, fullFriends));
    }

    @DeleteMapping("/{friendId}")
    public ResponseEntity<FriendListDto> removeFriend(@PathVariable final UUID friendId, @AuthenticationPrincipal Jwt token) {
        UserId userId = UserId.fromToken(token);
        friendService.removeFriendFromFriendList(userId, new UserId(friendId));

        PlatformUser user = friendService.findUserWithFriends(userId);
        List<UserId> friendIds = extractFriendIds(user.getFriends(), userId);
        List<PlatformUser> fullFriends = userService.findUserListByIdList(friendIds);

        return ResponseEntity.ok(FriendListDto.from(user, fullFriends));
    }

    @PatchMapping("/{friendId}/accept")
    public ResponseEntity<FriendDto> acceptFriendRequest(@PathVariable final UUID friendId, @AuthenticationPrincipal Jwt token) {
        UserId userId = UserId.fromToken(token);
        PlatformUserFriend newFriend = friendService.acceptFriendRequest(userId, friendId);

        UUID otherUserId = newFriend.getId().getUserId().equals(userId.id())
                ? newFriend.getId().getFriendId()
                : newFriend.getId().getUserId();
        PlatformUser friendUser = userService.findUserById(new UserId(otherUserId));

        return ResponseEntity.ok(FriendDto.from(friendUser));
    }

    @PatchMapping("/{friendId}/deny")
    public ResponseEntity denyFriendRequest(@PathVariable final UUID friendId, @AuthenticationPrincipal Jwt token) {
        UserId userId = UserId.fromToken(token);
        friendService.denyFriendRequest(userId, friendId);
        return ResponseEntity.ok("Friendrequest succesfully denied.");
    }

    @GetMapping("/requests")
    public ResponseEntity<FriendRequestListDto> findAllFriendRequests(@AuthenticationPrincipal Jwt token) {
        UserId userId = UserId.fromToken(token);
        List<PlatformUserFriend> friendRequests = friendService.findFriendRequestsForUser(userId);

        List<UserId> requestingUserIds = friendRequests.stream()
                .map(request -> {
                    UUID requestingUserId = request.getId().getUserId().equals(userId.id())
                            ? request.getId().getFriendId()
                            : request.getId().getUserId();
                    return new UserId(requestingUserId);
                })
                .toList();

        List<PlatformUser> requestingUsers = userService.findUserListByIdList(requestingUserIds);

        return ResponseEntity.ok(FriendRequestListDto.from(requestingUsers));
    }

    private List<UserId> extractFriendIds(List<PlatformUserFriend> friends, UserId currentUserId) {
        return friends.stream()
                .map(friend -> {
                    UUID friendId = friend.getId().getUserId().equals(currentUserId.id())
                            ? friend.getId().getFriendId()
                            : friend.getId().getUserId();
                    return new UserId(friendId);
                })
                .toList();
    }

}
