package be.kdg.ipj3.platformbackend.user.infrastructure.jpa.repository;

import be.kdg.ipj3.platformbackend.user.infrastructure.jpa.entity.JpaFriendRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;
import java.util.Optional;


public interface JpaFriendRepository extends JpaRepository<JpaFriendRequestEntity, UUID> {

    List<JpaFriendRequestEntity> findALlBySender_IdOrReceiver_IdAndIsConfirmed(UUID senderId, UUID receiverId, Boolean isConfirmed);
    List<JpaFriendRequestEntity> findAllBySender_IdAndIsConfirmed(UUID senderId,boolean isConfirmed);
    List<JpaFriendRequestEntity> findAllByReceiver_IdAndIsConfirmed(UUID senderId, boolean isConfirmed);
    Optional<JpaFriendRequestEntity> findBySender_IdAndReceiver_IdAndIsConfirmed(UUID senderId, UUID receiverId, Boolean isConfirmed);
    Optional<JpaFriendRequestEntity> findBySenderIdAndReceiverId(UUID senderId, UUID receiverId);

    @Query("SELECT f FROM JpaFriendRequestEntity f " +
            "WHERE ((f.sender.id = :userId AND f.receiver.id = :friendId) " +
            "   OR (f.sender.id = :friendId AND f.receiver.id = :userId)) " +
            "AND f.isConfirmed = false")
    Optional<JpaFriendRequestEntity> findPendingFriendRequest(@Param("userId") UUID userId,
                                                              @Param("friendId") UUID friendId);

    @Query("""
    SELECT DISTINCT CASE
           WHEN f.sender.id = :userId THEN f.receiver.id
           ELSE f.sender.id
           END
    FROM JpaFriendRequestEntity f
    WHERE (:userId = f.sender.id OR :userId = f.receiver.id)
""")
    List<UUID> findFriendIdsByUserId(@Param("userId") UUID userId);

    void removeById(UUID id);
}