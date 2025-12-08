package be.kdg.ipj3.platformbackend.user.infrastructure;

import be.kdg.ipj3.platformbackend.shared.domain.UserId;
import be.kdg.ipj3.platformbackend.user.domain.repository.PlatformUserFriendRepository;
import be.kdg.ipj3.platformbackend.user.domain.PlatformUserFriend;
import be.kdg.ipj3.platformbackend.user.domain.PlatformUserFriendId;
import be.kdg.ipj3.platformbackend.user.infrastructure.jpa.entity.JpaPlatformUserEntity;
import be.kdg.ipj3.platformbackend.user.infrastructure.jpa.entity.JpaFriendRequestEntity;
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


    //Todo : orElseThrow isn't throwing anything right now :'(
    @Override
    public PlatformUserFriend save(PlatformUserFriend user) {
        JpaPlatformUserEntity userEntity= jpaPlatformUserRepository.findById(user.getSender().getUserId().id()).orElseThrow();
        JpaPlatformUserEntity friendEntity= jpaPlatformUserRepository.findById(user.getReceiver().getUserId().id()).orElseThrow();
        return jpaFriendRepository.save(JpaFriendRequestEntity.fromDomain(user, userEntity, friendEntity)).toDomain();
    }

    @Override
    public PlatformUserFriend findFriendRequestBetween(UUID friendId, UserId userId) {
        jpaPlatformUserRepository.findById(friendId).orElseThrow(userId::notFound);
        return jpaFriendRepository.findByUserAndFriendAndConfirmationStatus(userId.id(), friendId, false)
                .orElseThrow(() -> new PlatformUserFriendId(userId.id(), friendId).notFound())
                .toDomain();
    }

    @Override
    public void remove(UserId userId, UserId friendId) {
        PlatformUserFriend friendRelation = jpaFriendRepository.findByUserAndFriendAndConfirmationStatus(userId.id(), friendId.id(), false)
                .orElseThrow(() -> new PlatformUserFriendId(userId.id(), friendId.id()).notFound())
                .toDomain();

        jpaFriendRepository.removeJpaFriendRequestEntityById(friendRelation.getId());
    }

    @Override
    public List<PlatformUserFriend> findAllFriendRequestsForUser(UserId userId) {
        return jpaFriendRepository
                .findAllFriendRequestByUserOrFriend(userId.id())
                .stream()
                .map(JpaFriendRequestEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void validateIfFriendRelationExists(UserId userId, UserId friendId) {
        jpaFriendRepository.findBySenderIdAndReceiverId(userId.id(), friendId.id())
                        .ifPresent(match -> {
                            throw new PlatformUserFriendId(userId.id(), friendId.id()).conflict();
                        });
    }

    @Override
    public List<UUID> getUniqueFriendIdsForUser(UUID userId) {
        return jpaFriendRepository.findFriendIdsByUserId(userId);
    }

}
