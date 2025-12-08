package be.kdg.ipj3.platformbackend.user.application;

import be.kdg.ipj3.platformbackend.shared.domain.UserId;
import be.kdg.ipj3.platformbackend.user.domain.repository.PlatformUserRepository;
import be.kdg.ipj3.platformbackend.user.domain.PlatformUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
@Slf4j
public class UserService {

    private final PlatformUserRepository platformUserRepository;

    public UserService(PlatformUserRepository platformUserRepository) {
        this.platformUserRepository = platformUserRepository;
    }

    public PlatformUser addUser(UserId userId) {
        PlatformUser user = new PlatformUser(userId, new ArrayList<>(), new ArrayList<>());
        return platformUserRepository.createUser(user);
    }
}
