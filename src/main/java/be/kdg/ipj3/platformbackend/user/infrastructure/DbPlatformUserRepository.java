package be.kdg.ipj3.platformbackend.user.infrastructure;

import be.kdg.ipj3.platformbackend.shared.domain.UserId;
import be.kdg.ipj3.platformbackend.shared.domain.exception.NotFoundException;
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
        List<JpaPlatformUserFriendEntity> friendsDb = jpaFriendRepository.findAllByUserOrFriend(userId.id());
        List<PlatformUserFriend> confirmedFriends = friendsDb.stream()
                .map(JpaPlatformUserFriendEntity::toDomain)
                .collect(Collectors.toList());
        return PlatformUser.fromDb(userId.id(), confirmedFriends);
    }

    @Override
    public PlatformUser findUserById(UserId userId) {
        return jpaPlatformUserRepository.findById(userId.id()).orElseThrow(userId::notFound).toDomain();
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
    public List<JpaPlatformUserEntity> findRecommendationsListOfSize( List<UUID> excludedIds, int size) {
        return jpaPlatformUserRepository.findUsersNotInListLimited(excludedIds, size);
    }

    @Override
    public List<JpaPlatformUserEntity> findRecommendationsListOfSizeWithNameQuery( List<UUID> excludedIds,String nameQuery, int size) {
        return jpaPlatformUserRepository.findUsersNotInListWithNameQuery(excludedIds,nameQuery, size);
    }

}