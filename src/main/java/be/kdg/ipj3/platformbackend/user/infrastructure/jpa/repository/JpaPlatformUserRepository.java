package be.kdg.ipj3.platformbackend.user.infrastructure.jpa.repository;

import be.kdg.ipj3.platformbackend.user.infrastructure.jpa.entity.JpaPlatformUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

public interface JpaPlatformUserRepository extends JpaRepository<JpaPlatformUserEntity, UUID> {

    Optional<JpaPlatformUserEntity> findByUserName(String userName);



    @Query(value = """
    SELECT *
    FROM platform_user u
    WHERE u.id NOT IN (:excludedIds)
      AND LOWER(u.user_name) LIKE LOWER(CONCAT('%', :nameQuery, '%'))
    LIMIT :size
""", nativeQuery = true)
    List<JpaPlatformUserEntity> findUsersNotInListWithNameQuery(
            @Param("excludedIds") List<UUID> excludedIds,
            @Param("nameQuery") String nameQuery,
            @Param("size") int size);

    @Query(value = """
        SELECT *
        FROM platform_user u
        WHERE u.id NOT IN (:excludedIds)
        LIMIT :size
    """, nativeQuery = true)
    List<JpaPlatformUserEntity> findUsersNotInListLimited(
            @Param("excludedIds") List<UUID> excludedIds,
            @Param("size") int size);
}
