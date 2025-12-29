package be.kdg.ipj3.platformbackend.lobby.infrastructure.jpa.entity;

import be.kdg.ipj3.platformbackend.lobby.domain.Lobby;
import be.kdg.ipj3.platformbackend.lobby.domain.LobbyId;
import be.kdg.ipj3.platformbackend.lobby.domain.Player;
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

    public Lobby toDomain(){
        List<Player> domainPlayers = this.players.stream().map(JpaPlayerEntity::toDomain).toList();
        return new Lobby(new LobbyId(id), domainPlayers,this.creationDate, this.maxPlayerCount );
    }

    public static JpaLobbyEntity fromDomain(Lobby domain){
        JpaLobbyEntity entity = new JpaLobbyEntity();
        entity.id = domain.getId().id();
        entity.creationDate = domain.getCreationDate();
        entity.maxPlayerCount = domain.getMaxPlayerCount();
        entity.players = domain.getPlayers()
                .stream()
                .map(JpaPlayerEntity::fromDomain)
                .toList();
        return entity;
    }

}