package be.kdg.ipj3.platformbackend.lobby.infrastructure.jpa.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "lobbies")
public class JpaLobbyEntity {
    @Id
    private UUID id;
    LocalDateTime creationDate;
    int maxPlayerCount;
    @OneToMany(cascade = CascadeType.ALL)
    List<JpaPlayerEntity> players;
}
