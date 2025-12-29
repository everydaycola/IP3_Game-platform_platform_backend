package be.kdg.ipj3.platformbackend.lobby.infrastructure.jpa.entity;

import be.kdg.ipj3.platformbackend.lobby.domain.Player;
import be.kdg.ipj3.platformbackend.user.infrastructure.jpa.entity.JpaPlatformUserEntity;
import jakarta.persistence.*;


@Entity
@Table(name = "players")
public class JpaPlayerEntity {
    @Id
    @ManyToOne
    @JoinColumn(name = "platform_user_id", nullable = false)
    private JpaPlatformUserEntity user;

    @ManyToOne
    @JoinColumn(name = "lobby_id", nullable = false)
    private JpaLobbyEntity lobby;

    public Player toDomain() {
        return new Player(user.toDomain());
    }

    public static JpaPlayerEntity fromDomain(Player domain, JpaLobbyEntity lobby) {
        JpaPlayerEntity entity = new JpaPlayerEntity();
        entity.user = JpaPlatformUserEntity.fromDomain(domain.user());
        entity.lobby = lobby;
        return entity;
    }
}