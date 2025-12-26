package be.kdg.ipj3.platformbackend.lobby.infrastructure.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class JpaPlayerEntity {
    @Id
    private JpaPlayerId id;
}
