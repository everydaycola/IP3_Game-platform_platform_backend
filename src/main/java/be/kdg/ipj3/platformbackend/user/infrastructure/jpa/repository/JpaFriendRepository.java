package be.kdg.ipj3.platformbackend.user.infrastructure.jpa.repository;

import be.kdg.ipj3.platformbackend.user.infrastructure.jpa.entity.JpaPlatformUserFriendEntity;
import be.kdg.ipj3.platformbackend.user.infrastructure.jpa.entity.JpaPlatformUserFriendId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;
import java.util.Optional;


public interface JpaFriendRepository extends JpaRepository<JpaPlatformUserFriendEntity, JpaPlatformUserFriendId> {

    @Query("""
    SELECT f
    FROM JpaPlatformUserFriendEntity f
    WHERE f.isConfirmed = false
      AND (f.user.id = :id OR f.friend.id = :id)
    """)
    List<JpaPlatformUserFriendEntity> findAllFriendRequestByUserOrFriend(@Param("id") UUID id);

    @Query("""
    SELECT f
    FROM JpaPlatformUserFriendEntity f
    WHERE f.isConfirmed = true
      AND (f.user.id = :id OR f.friend.id = :id)
""")
    List<JpaPlatformUserFriendEntity> findAllByUserOrFriend(@Param("id") UUID id);

    Optional<JpaPlatformUserFriendEntity> findByUser_IdAndFriend_IdAndIsConfirmedTrue(UUID userId, UUID friendId);
    Optional<JpaPlatformUserFriendEntity> findByUser_IdAndFriend_IdAndIsConfirmedFalse(UUID friendId, UUID userId);
    void removeById(JpaPlatformUserFriendId id);
    Optional<JpaPlatformUserFriendEntity> findFriendById(JpaPlatformUserFriendId id);
}