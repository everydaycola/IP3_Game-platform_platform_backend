package be.kdg.ipj3.platformbackend.user.infrastructure;

import be.kdg.ipj3.platformbackend.shared.domain.UserId;
import be.kdg.ipj3.platformbackend.shared.domain.exception.ConflictException;
import be.kdg.ipj3.platformbackend.shared.domain.exception.NotFoundException;
import be.kdg.ipj3.platformbackend.user.domain.repository.PlatformUserFriendRepository;
import be.kdg.ipj3.platformbackend.user.domain.PlatformFriendRequest;
import be.kdg.ipj3.platformbackend.user.infrastructure.jpa.entity.JpaPlatformUserEntity;
import be.kdg.ipj3.platformbackend.user.infrastructure.jpa.entity.JpaFriendRequestEntity;
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
    public PlatformFriendRequest save(PlatformFriendRequest user) {
        JpaPlatformUserEntity userEntity = jpaPlatformUserRepository.findById(user.getSender().getUserId().id()).orElseThrow();
        JpaPlatformUserEntity friendEntity = jpaPlatformUserRepository.findById(user.getReceiver().getUserId().id()).orElseThrow();
        return jpaFriendRepository.save(JpaFriendRequestEntity.fromDomain(user, userEntity, friendEntity)).toDomain();
    }

    @Override
    public PlatformFriendRequest findFriendRequestBetween(UUID friendId, UserId userId) {
        jpaPlatformUserRepository.findById(friendId).orElseThrow(userId::notFound);
        return jpaFriendRepository
                .findBySender_IdAndReceiver_IdAndIsConfirmed(userId.id(), friendId, false)
                .or(() -> jpaFriendRepository.findBySender_IdAndReceiver_IdAndIsConfirmed(friendId, userId.id(), false))
                .orElseThrow(() -> new NotFoundException("Friend request not found"))
                .toDomain();
    }

    @Override
    public void remove(UserId userId, UserId friendId,boolean isConfirmed) {
        JpaFriendRequestEntity friendRelation = jpaFriendRepository.findBySender_IdAndReceiver_IdAndIsConfirmed(userId.id(), friendId.id(), isConfirmed)
                .or(() -> jpaFriendRepository.findBySender_IdAndReceiver_IdAndIsConfirmed(friendId.id(), userId.id(), false))
                .orElseThrow(() -> new NotFoundException("Friend request not found"));

        friendRelation.getSender().getSentFriendRequests().remove(friendRelation);
        friendRelation.getSender().getReceivedFriendRequests().remove(friendRelation);
        friendRelation.getReceiver().getSentFriendRequests().remove(friendRelation);
        friendRelation.getReceiver().getReceivedFriendRequests().remove(friendRelation);

        jpaPlatformUserRepository.save(friendRelation.getSender());
        jpaPlatformUserRepository.save(friendRelation.getReceiver());
        jpaFriendRepository.delete(friendRelation);
    }

    @Override
    public List<PlatformFriendRequest> findAllFriendRequestsForUser(UserId userId) {
        return jpaFriendRepository
                .findAllByReceiver_IdAndIsConfirmed(userId.id(), false)
                .stream()
                .map(JpaFriendRequestEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void validateIfFriendRelationExists(UserId userId, UserId friendId) {
        jpaFriendRepository.findBySenderIdAndReceiverId(userId.id(), friendId.id())
                .ifPresent(match -> {
                    throw new ConflictException("There already is a relation between " + userId.id() + " and " + friendId.id());
                });
    }

    @Override
    public List<UUID> getUniqueFriendIdsForUser(UUID userId) {
        return jpaFriendRepository.findFriendIdsByUserId(userId);
    }

}
