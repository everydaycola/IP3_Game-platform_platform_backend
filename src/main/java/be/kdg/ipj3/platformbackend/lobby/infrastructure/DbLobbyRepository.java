package be.kdg.ipj3.platformbackend.lobby.infrastructure;

import be.kdg.ipj3.platformbackend.lobby.domain.Lobby;
import be.kdg.ipj3.platformbackend.lobby.domain.LobbyId;
import be.kdg.ipj3.platformbackend.lobby.domain.repository.LobbyRepository;
import be.kdg.ipj3.platformbackend.lobby.infrastructure.jpa.entity.JpaLobbyEntity;
import be.kdg.ipj3.platformbackend.lobby.infrastructure.jpa.repository.JpaLobbyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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

    //TODO: Make sure the max players is set based on what the game expects.
    @Override
    public Lobby createNewLobby(Lobby newLobby) {
        JpaLobbyEntity createdLobby=jpaLobbyRepository.save(JpaLobbyEntity.fromDomain(newLobby));
        return createdLobby.toDomain();
    }

    // @Override
    //    public Optional<PlatformUser> findUserById(UserId userId) {
    //        return jpaPlatformUserRepository.findByIdWithAchievementsAndOwnedGames(userId.id()).map(JpaPlatformUserEntity::toDomain);
    //    }

    @Override
    public Optional<Lobby> findLobbyById(LobbyId lobbyId) {
        return jpaLobbyRepository.findById(lobbyId.id()).map(JpaLobbyEntity::toDomain);
    }

    @Override
    public void save(Lobby lobby) {
        jpaLobbyRepository.save(JpaLobbyEntity.fromDomain(lobby));
    }

    @Override
    public void remove(Lobby lobby) {
        jpaLobbyRepository.delete(JpaLobbyEntity.fromDomain(lobby));
    }
}
