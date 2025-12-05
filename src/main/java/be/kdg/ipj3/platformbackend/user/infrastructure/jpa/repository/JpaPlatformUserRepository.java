package be.kdg.ipj3.platformbackend.user.infrastructure.jpa.repository;

import be.kdg.ipj3.platformbackend.user.infrastructure.jpa.entity.JpaPlatformUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface JpaPlatformUserRepository extends JpaRepository<JpaPlatformUserEntity, UUID> {
    @Query("SELECT u FROM JpaPlatformUserEntity u LEFT JOIN FETCH u.friends WHERE u.id = :userId")
    JpaPlatformUserEntity findByIdWithFriends(@Param("userId") UUID userId);
}
