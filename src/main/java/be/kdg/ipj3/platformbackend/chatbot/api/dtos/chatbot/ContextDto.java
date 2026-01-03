package be.kdg.ipj3.platformbackend.chatbot.api.dtos.chatbot;

import java.util.UUID;

public record ContextDto(String teamId, String gameName, String pageUrl) {
}
