package be.kdg.ipj3.platformbackend.user.infrastructure;

import be.kdg.ipj3.platformbackend.shared.domain.UserId;
import be.kdg.ipj3.platformbackend.user.domain.repository.PlatformUserRepository;
import be.kdg.ipj3.platformbackend.user.domain.PlatformUser;
import be.kdg.ipj3.platformbackend.user.domain.PlatformUserFriend;
import be.kdg.ipj3.platformbackend.user.infrastructure.jpa.entity.JpaPlatformUserEntity;
import be.kdg.ipj3.platformbackend.user.infrastructure.jpa.entity.JpaPlatformUserFriendEntity;
import be.kdg.ipj3.platformbackend.user.infrastructure.jpa.repository.JpaFriendRepository;
import be.kdg.ipj3.platformbackend.user.infrastructure.jpa.repository.JpaPlatformUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class DbPlatformUserRepository implements PlatformUserRepository {

    private final JpaPlatformUserRepository jpaPlatformUserRepository;
    private final JpaFriendRepository jpaFriendRepository;

    public DbPlatformUserRepository(JpaPlatformUserRepository jpaPlatformUserRepository, JpaFriendRepository jpaFriendRepository) {
        this.jpaPlatformUserRepository = jpaPlatformUserRepository;
        this.jpaFriendRepository = jpaFriendRepository;
    }

    @Override
    public PlatformUser findByIdWithFriends(UserId userId) {
        List<JpaPlatformUserFriendEntity> friendsDb = jpaFriendRepository.findAllByUser_IdAndIsConfirmedTrue(userId.id());
        List<PlatformUserFriend> confirmedFriends = friendsDb.stream()
                .map(JpaPlatformUserFriendEntity::toDomain)
                .collect(Collectors.toList());
        return PlatformUser.fromDb(userId.id(),confirmedFriends);
    }

    @Override
    public PlatformUser findUserById(UserId userId) {
        return jpaPlatformUserRepository.findById(userId.id()).orElseThrow(userId::notFound).toDomain();
    }


    @Override
    public void save(PlatformUser user) {
        jpaPlatformUserRepository.save(JpaPlatformUserEntity.fromDomain(user));
    }


    @Override
    public PlatformUser createUser(PlatformUser user) {
        return jpaPlatformUserRepository.save(JpaPlatformUserEntity.fromDomain(user)).toDomain();
    }
}