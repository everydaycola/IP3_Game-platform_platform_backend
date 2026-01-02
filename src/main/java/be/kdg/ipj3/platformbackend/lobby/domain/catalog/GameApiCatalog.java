package be.kdg.ipj3.platformbackend.lobby.domain.catalog;

import be.kdg.ipj3.platformbackend.game.domain.Game;

import java.util.UUID;

public interface GameApiCatalog {
    UUID startGameSession(Game game, String authToken, UUID player1Id, UUID player2Id);
}
