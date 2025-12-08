package be.kdg.ipj3.platformbackend.user.application;

import be.kdg.ipj3.platformbackend.shared.domain.UserId;
import be.kdg.ipj3.platformbackend.user.domain.repository.PlatformUserRepository;
import be.kdg.ipj3.platformbackend.user.domain.PlatformUser;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@Transactional
public class UserService {

    private final PlatformUserRepository platformUserRepository;

    public UserService(PlatformUserRepository platformUserRepository) {
        this.platformUserRepository = platformUserRepository;
    }

    public PlatformUser addUser(UserId userId, String userName) {
        PlatformUser user = new PlatformUser(userId,userName, "", new ArrayList<>());
        return platformUserRepository.createUser(user);
    }

    public List<PlatformUser> findUserListByIdList(List<UserId> friendIds) {
        List<UUID> uuids = friendIds.stream()
                .map(UserId::id)
                .toList();

        return platformUserRepository.findAllUsersByIds(uuids);
    }

    public PlatformUser findUserById(UserId userId) {
        return platformUserRepository.findUserById(userId);
    }

    public PlatformUser findUserByUserName(String userName) {
        return platformUserRepository.findUserByUserName(userName);
    }
}
