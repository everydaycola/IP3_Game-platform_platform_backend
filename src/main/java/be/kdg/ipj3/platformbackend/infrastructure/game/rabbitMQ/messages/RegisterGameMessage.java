package be.kdg.ipj3.platformbackend.infrastructure.game.rabbitMQ.messages;

import be.kdg.ipj3.platformbackend.api.dtos.game.FullGameDto;

public record RegisterGameMessage(FullGameDto gameDto) {
}
