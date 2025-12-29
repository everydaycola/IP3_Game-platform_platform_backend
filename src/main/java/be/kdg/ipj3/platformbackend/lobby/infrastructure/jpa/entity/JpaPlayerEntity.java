package be.kdg.ipj3.platformbackend.lobby.infrastructure.jpa.entity;

import be.kdg.ipj3.platformbackend.lobby.domain.LobbyId;
import be.kdg.ipj3.platformbackend.lobby.domain.Player;
import be.kdg.ipj3.platformbackend.shared.domain.UserId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class JpaPlayerEntity {
    @Id
    private JpaPlayerId id;

    public Player toDomain() {
        return new Player(new UserId(this.id.getUserId()), new LobbyId(this.id.getLobbyId()));
    }

    public static JpaPlayerEntity fromDomain(Player domain) {
        JpaPlayerEntity entity = new JpaPlayerEntity();
        entity.id = new JpaPlayerId(
                domain.userId().id(),
                domain.lobbyId().id()
        );
        return entity;
    }
}