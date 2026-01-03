package be.kdg.ipj3.platformbackend.chatbot.domain;

import be.kdg.ipj3.platformbackend.shared.domain.UserId;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class Conversation {
    private final ConversationId id;
    private final UserId user;
    private final List<ChatMessage> messages;
}
