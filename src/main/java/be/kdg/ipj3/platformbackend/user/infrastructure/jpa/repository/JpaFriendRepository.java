package be.kdg.ipj3.platformbackend.user.infrastructure.jpa.repository;

import be.kdg.ipj3.platformbackend.user.infrastructure.jpa.entity.JpaPlatformUserFriendEntity;
import be.kdg.ipj3.platformbackend.user.infrastructure.jpa.entity.JpaPlatformUserFriendId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;
import java.util.Optional;


public interface JpaFriendRepository extends JpaRepository<JpaPlatformUserFriendEntity, JpaPlatformUserFriendId> {
    List<JpaPlatformUserFriendEntity> findAllByUser_IdAndIsConfirmedTrue(UUID id);
    Optional<JpaPlatformUserFriendEntity> findByUser_IdAndFriend_IdAndIsConfirmedTrue(UUID userId, UUID friendId);
    Optional<JpaPlatformUserFriendEntity> findByUser_IdAndFriend_IdAndIsConfirmedFalse(UUID friendId, UUID userId);
    void removeById(JpaPlatformUserFriendId id);
}