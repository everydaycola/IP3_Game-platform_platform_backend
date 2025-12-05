package be.kdg.ipj3.platformbackend.user.api;

import be.kdg.ipj3.platformbackend.shared.domain.UserId;
import be.kdg.ipj3.platformbackend.user.api.dtos.FriendListDto;
import be.kdg.ipj3.platformbackend.user.api.dtos.PlatformUserDto;
import be.kdg.ipj3.platformbackend.user.application.FriendService;
import be.kdg.ipj3.platformbackend.user.application.UserService;
import be.kdg.ipj3.platformbackend.user.domain.PlatformUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    public ResponseEntity<PlatformUserDto> addUser(@AuthenticationPrincipal Jwt token) {
        UserId userId = UserId.fromToken(token);
        return ResponseEntity.ok(PlatformUserDto.from(userService.addUser(userId)));
    }




}
