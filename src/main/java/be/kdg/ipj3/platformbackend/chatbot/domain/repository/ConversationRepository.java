package be.kdg.ipj3.platformbackend.chatbot.domain.repository;

import be.kdg.ipj3.platformbackend.chatbot.domain.ChatMessage;
import be.kdg.ipj3.platformbackend.chatbot.domain.Conversation;
import be.kdg.ipj3.platformbackend.chatbot.domain.ConversationId;

import java.util.List;

public interface ConversationRepository {
    void save (Conversation conversation);
    Conversation findById (ConversationId id);
    List<ChatMessage> findAllMessagesByConversationId(ConversationId id);
}
