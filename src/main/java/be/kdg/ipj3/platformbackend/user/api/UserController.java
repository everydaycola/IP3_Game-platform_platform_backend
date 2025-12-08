package be.kdg.ipj3.platformbackend.user.api;

import be.kdg.ipj3.platformbackend.shared.domain.UserId;
import be.kdg.ipj3.platformbackend.user.api.dtos.PlatformUserDto;
import be.kdg.ipj3.platformbackend.user.application.UserService;
import be.kdg.ipj3.platformbackend.user.domain.PlatformUser;
import be.kdg.ipj3.platformbackend.user.helpers.JwtHelpers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

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
        PlatformUser user;
        try {
            user = userService.findUserById(userId);
        }catch(Exception e){
            user = userService.addUser(userId, userName);
        }
        log.info("User with id {} was recognized by the platform", userId);
        return ResponseEntity.ok(PlatformUserDto.from(user));
    }

}
