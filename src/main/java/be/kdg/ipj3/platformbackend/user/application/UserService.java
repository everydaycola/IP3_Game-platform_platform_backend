package be.kdg.ipj3.platformbackend.user.application;

import be.kdg.ipj3.platformbackend.shared.domain.UserId;
import be.kdg.ipj3.platformbackend.user.application.repository.PlatformUserRepository;
import be.kdg.ipj3.platformbackend.user.domain.PlatformUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Slf4j
public class UserService {

    private final PlatformUserRepository platformUserRepository;

    public UserService(PlatformUserRepository platformUserRepository) {
        this.platformUserRepository = platformUserRepository;
    }

    public PlatformUser getUser(UserId userId) {
        return platformUserRepository.findOne(userId);
    }

    public PlatformUser addUser(UserId userId) {
        PlatformUser user = new PlatformUser(userId, new ArrayList<>());
        return platformUserRepository.createUser(user);
    }
}
