package be.kdg.ipj3.platformbackend.lobby.api.dtos.request;

import java.util.UUID;

public record StartGameRequest(UUID player1Id, UUID player2Id) {}

