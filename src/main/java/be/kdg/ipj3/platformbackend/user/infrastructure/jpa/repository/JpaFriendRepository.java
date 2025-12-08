package be.kdg.ipj3.platformbackend.user.infrastructure.jpa.repository;

import be.kdg.ipj3.platformbackend.user.infrastructure.jpa.entity.JpaFriendRequestEntity;
import be.kdg.ipj3.platformbackend.user.infrastructure.jpa.entity.JpaPlatformUserFriendId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;
import java.util.Optional;


public interface JpaFriendRepository extends JpaRepository<JpaFriendRequestEntity, JpaPlatformUserFriendId> {

    @Query("""
    SELECT f
    FROM JpaFriendRequestEntity f
    WHERE f.isConfirmed = false
      AND (f.sender.id = :id OR f.receiver.id = :id)
    """)
    List<JpaFriendRequestEntity> findAllFriendRequestByUserOrFriend(@Param("id") UUID id);

    @Query("""
    SELECT f
    FROM JpaFriendRequestEntity f
    WHERE f.isConfirmed = true
      AND (f.sender.id = :id OR f.receiver.id = :id)
    """)
    List<JpaFriendRequestEntity> findAllByUserOrFriend(@Param("id") UUID id);

    @Query("""
    SELECT f
    FROM JpaFriendRequestEntity f
    WHERE f.isConfirmed = :isConfirmed
      AND ((f.sender.id = :userId AND f.receiver.id = :friendId)
           OR (f.sender.id = :friendId AND f.receiver.id = :userId))
""")
    Optional<JpaFriendRequestEntity> findByUserAndFriendAndConfirmationStatus(@Param("userId") UUID userId, @Param("friendId") UUID friendId, @Param("isConfirmed") boolean isConfirmed);

    @Query("""
    SELECT DISTINCT CASE
           WHEN f.sender.id = :userId THEN f.receiver.id
           ELSE f.sender.id
           END
    FROM JpaFriendRequestEntity f
    WHERE (:userId = f.sender.id OR :userId = f.receiver.id)
""")
    List<UUID> findFriendIdsByUserId(@Param("userId") UUID userId);
    void removeById(JpaPlatformUserFriendId id);
    Optional<JpaFriendRequestEntity> findFriendById(JpaPlatformUserFriendId id);
    Optional<JpaFriendRequestEntity> findBySenderIdAndReceiverId(UUID senderId, UUID receiverId);
    void removeJpaFriendRequestEntityById(UUID id);
}