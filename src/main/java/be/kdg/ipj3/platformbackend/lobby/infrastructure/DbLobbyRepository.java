package be.kdg.ipj3.platformbackend.lobby.infrastructure;

import be.kdg.ipj3.platformbackend.lobby.domain.Lobby;
import be.kdg.ipj3.platformbackend.lobby.domain.repository.LobbyRepository;
import be.kdg.ipj3.platformbackend.lobby.infrastructure.jpa.entity.JpaLobbyEntity;
import be.kdg.ipj3.platformbackend.lobby.infrastructure.jpa.repository.JpaLobbyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class DbLobbyRepository implements LobbyRepository {

    private final JpaLobbyRepository jpaLobbyRepository;

    public DbLobbyRepository(JpaLobbyRepository jpaLobbyRepository) {
        this.jpaLobbyRepository = jpaLobbyRepository;
    }

    @Override
    public List<Lobby> findAllLobbies() {
        return jpaLobbyRepository.findAll().stream().map(JpaLobbyEntity::toDomain).toList();
    }

    //Todo implement extra logic such as what game the lobby was bound to
    //And set the "maxplayers" based on this game's requirements.
    @Override
    public Lobby createNewLobby(Lobby newLobby) {
        JpaLobbyEntity createdLobby=jpaLobbyRepository.save(JpaLobbyEntity.fromDomain(newLobby));
        return createdLobby.toDomain();
    }
}
