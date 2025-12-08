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

    @Query("""
    SELECT f
    FROM JpaPlatformUserFriendEntity f
    WHERE f.isConfirmed = :isConfirmed
      AND ((f.user.id = :userId AND f.friend.id = :friendId)
           OR (f.user.id = :friendId AND f.friend.id = :userId))
""")
    Optional<JpaPlatformUserFriendEntity> findByUserAndFriendAndConfirmationStatus(@Param("userId") UUID userId,@Param("friendId") UUID friendId, @Param("isConfirmed") boolean isConfirmed);

    @Query("""
    SELECT DISTINCT CASE
           WHEN f.user.id = :userId THEN f.friend.id
           ELSE f.user.id
           END
    FROM JpaPlatformUserFriendEntity f
    WHERE f.isConfirmed = true
      AND (:userId = f.user.id OR :userId = f.friend.id)
""")
    List<UUID> findFriendIdsByUserId(@Param("userId") UUID userId);


    void removeById(JpaPlatformUserFriendId id);
    Optional<JpaPlatformUserFriendEntity> findFriendById(JpaPlatformUserFriendId id);
}