package be.kdg.ipj3.platformbackend.user.infrastructure;

import be.kdg.ipj3.platformbackend.shared.domain.UserId;
import be.kdg.ipj3.platformbackend.shared.domain.exception.NotFoundException;
import be.kdg.ipj3.platformbackend.user.domain.repository.PlatformUserFriendRepository;
import be.kdg.ipj3.platformbackend.user.domain.PlatformUserFriend;
import be.kdg.ipj3.platformbackend.user.domain.PlatformUserFriendId;
import be.kdg.ipj3.platformbackend.user.infrastructure.jpa.entity.JpaPlatformUserEntity;
import be.kdg.ipj3.platformbackend.user.infrastructure.jpa.entity.JpaPlatformUserFriendEntity;
import be.kdg.ipj3.platformbackend.user.infrastructure.jpa.entity.JpaPlatformUserFriendId;
import be.kdg.ipj3.platformbackend.user.infrastructure.jpa.repository.JpaFriendRepository;
import be.kdg.ipj3.platformbackend.user.infrastructure.jpa.repository.JpaPlatformUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class DbPlatformUserFriendRepository implements PlatformUserFriendRepository {

    private final JpaPlatformUserRepository jpaPlatformUserRepository;
    private final JpaFriendRepository jpaFriendRepository;

    public DbPlatformUserFriendRepository(JpaPlatformUserRepository jpaPlatformUserRepository, JpaFriendRepository jpaFriendRepository) {
        this.jpaPlatformUserRepository = jpaPlatformUserRepository;
        this.jpaFriendRepository = jpaFriendRepository;
    }

    @Override
    public PlatformUserFriend findFriendById(UserId userId,UserId friendId) {
        return jpaFriendRepository.findByUser_IdAndFriend_IdAndIsConfirmedTrue(userId.id(),friendId.id()).orElseThrow(userId::notFound).toDomain();
    }

    @Override
    public PlatformUserFriend save(PlatformUserFriend user) {
        JpaPlatformUserEntity userEntity= jpaPlatformUserRepository.findById(user.getId().getUserId()).orElseThrow();
        JpaPlatformUserEntity friendEntity= jpaPlatformUserRepository.findById(user.getId().getFriendId()).orElseThrow();
        return jpaFriendRepository.save(JpaPlatformUserFriendEntity.fromDomain(user, userEntity, friendEntity)).toDomain();
    }

    @Override
    public PlatformUserFriend findFriendRequestBetween(UUID friendId, UserId userId) {
        jpaPlatformUserRepository.findById(friendId).orElseThrow(userId::notFound);
        return jpaFriendRepository.findByUser_IdAndFriend_IdAndIsConfirmedFalse(friendId, userId.id()).orElseThrow(userId::notFound).toDomain();
    }

    @Override
    public void remove(UserId userId, UserId friendId) {
        PlatformUserFriend friendRelation;
        try {
            friendRelation = findFriendById(userId, friendId);
        }catch(NotFoundException e){
            friendRelation = findFriendById(friendId, userId);
        }
        jpaFriendRepository.removeById(JpaPlatformUserFriendId.fromDomain(friendRelation.getId()));
    }

    @Override
    public List<PlatformUserFriend> findAllFriendRequestsForUser(UserId userId) {
        return jpaFriendRepository
                .findAllFriendRequestByUserOrFriend(userId.id())
                .stream()
                .map(JpaPlatformUserFriendEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void validateIfFriendRelationExists(UserId userId, UserId friendId) {
        jpaFriendRepository.findFriendById(JpaPlatformUserFriendId.fromDomain(new PlatformUserFriendId(userId.id(), friendId.id())))
                .ifPresent(match -> {
                    throw PlatformUserFriendId.conflict(userId.id(), friendId.id());
                });
    }

}
