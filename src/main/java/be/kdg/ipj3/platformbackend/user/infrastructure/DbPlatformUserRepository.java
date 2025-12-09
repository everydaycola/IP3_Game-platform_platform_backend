package be.kdg.ipj3.platformbackend.user.infrastructure;

import be.kdg.ipj3.platformbackend.shared.domain.UserId;
import be.kdg.ipj3.platformbackend.shared.domain.exception.NotFoundException;
import be.kdg.ipj3.platformbackend.user.domain.repository.PlatformUserRepository;
import be.kdg.ipj3.platformbackend.user.domain.PlatformUser;
import be.kdg.ipj3.platformbackend.user.domain.PlatformFriendRequest;
import be.kdg.ipj3.platformbackend.user.infrastructure.jpa.entity.JpaPlatformUserEntity;
import be.kdg.ipj3.platformbackend.user.infrastructure.jpa.entity.JpaFriendRequestEntity;
import be.kdg.ipj3.platformbackend.user.infrastructure.jpa.repository.JpaFriendRepository;
import be.kdg.ipj3.platformbackend.user.infrastructure.jpa.repository.JpaPlatformUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
        List<JpaFriendRequestEntity> sendersDb = jpaFriendRepository.findAllBySender_IdAndIsConfirmed(userId.id(), true);
        List<JpaFriendRequestEntity> receiverDb = jpaFriendRepository.findAllByReceiver_IdAndIsConfirmed(userId.id(),true);
        List<PlatformFriendRequest> senders = sendersDb.stream()
                .map(JpaFriendRequestEntity::toDomain)
                .toList();
        List<PlatformFriendRequest> receivers = receiverDb.stream()
                .map(JpaFriendRequestEntity::toDomain)
                .toList();

        return new PlatformUser(userId, "","", senders, receivers);
    }

    @Override
    public Optional<PlatformUser> findUserById(UserId userId) {
        return jpaPlatformUserRepository.findById(userId.id()).map(JpaPlatformUserEntity::toDomain);
    }

    @Override
    public List<PlatformUser> findAllUsersByIds(List<UUID> idList) {
        return jpaPlatformUserRepository.findAllById(idList).stream()
                .map(JpaPlatformUserEntity::toDomain)
                .toList();
    }


    @Override
    public void save(PlatformUser user) {
        jpaPlatformUserRepository.save(JpaPlatformUserEntity.fromDomain(user));
    }


    @Override
    public PlatformUser createUser(PlatformUser user) {
        return jpaPlatformUserRepository.save(JpaPlatformUserEntity.fromDomain(user)).toDomain();
    }

    @Override
    public PlatformUser findUserByUserName(String userName) {
        return jpaPlatformUserRepository
                .findByUserName(userName)
                .orElseThrow(() -> new NotFoundException("User with username '" + userName + "' not found"))
                .toDomain();
    }

    @Override
    public List<PlatformUser> findRecommendationsListOfSize( List<UUID> excludedIds, int size) {
        return jpaPlatformUserRepository.findUsersNotInListLimited(excludedIds, size)
                .stream()
                .map(JpaPlatformUserEntity::toDomain)
                .toList();
    }

    @Override
    public List<PlatformUser> findRecommendationsListOfSizeWithNameQuery( List<UUID> excludedIds,String nameQuery, int size) {
        return jpaPlatformUserRepository.findUsersNotInListWithNameQuery(excludedIds,nameQuery, size)
                .stream()
                .map(JpaPlatformUserEntity::toDomain)
                .toList();
    }

}