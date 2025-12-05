package be.kdg.ipj3.platformbackend.user.infrastructure;

import be.kdg.ipj3.platformbackend.shared.domain.UserId;
import be.kdg.ipj3.platformbackend.user.application.repository.PlatformUserRepository;
import be.kdg.ipj3.platformbackend.user.domain.PlatformUser;
import be.kdg.ipj3.platformbackend.user.infrastructure.jpa.entity.JpaPlatformUserEntity;
import be.kdg.ipj3.platformbackend.user.infrastructure.jpa.repository.JpaPlatformUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class DbPlatformUserRepository implements PlatformUserRepository {

    private final JpaPlatformUserRepository jpaPlatformUserRepository;

    public DbPlatformUserRepository(JpaPlatformUserRepository jpaPlatformUserRepository) {
        this.jpaPlatformUserRepository = jpaPlatformUserRepository;
    }

    @Override
    public PlatformUser findOneWithFriends(UserId userId) {
        return jpaPlatformUserRepository.findByIdWithFriends(userId.id()).toDomain();
    }

    @Override
    public PlatformUser findOne(UserId userId) {
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
