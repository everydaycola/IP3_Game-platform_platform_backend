package be.kdg.ipj3.platformbackend.game.infrastructure.jpa.repository;

import be.kdg.ipj3.platformbackend.game.domain.Genre;
import be.kdg.ipj3.platformbackend.game.infrastructure.jpa.entity.JpaGameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface JpaGameRepository extends JpaRepository<JpaGameEntity, UUID> {

    @Query("""
            select g from JpaGenreEntity g where g.name = :name
            """)
    Optional<Genre> findGenreByName(String name);
}
