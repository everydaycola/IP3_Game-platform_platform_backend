package be.kdg.ipj3.platformbackend.chatbot.application;

import be.kdg.ipj3.platformbackend.chatbot.domain.Conversation;
import be.kdg.ipj3.platformbackend.chatbot.domain.ConversationId;
import be.kdg.ipj3.platformbackend.chatbot.domain.repository.ConversationRepository;
import be.kdg.ipj3.platformbackend.shared.domain.UserId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
public class ConversationService {
    private final ConversationRepository repository;

    public ConversationService(ConversationRepository repository) {
        this.repository = repository;
    }

    Conversation find(ConversationId id){
        return repository.findById(id).orElseThrow(id::notFound);
    }

    Conversation startNew(UserId userId){
        Conversation newConversation = Conversation.startNew(userId);
        log.info("New Conversation {} stated", newConversation.getId().id());
        repository.save(newConversation);
        return newConversation;
    }

    Conversation sendMessage(ConversationId id, UUID sender, String text, LocalDateTime sendOn){
        Conversation conversation = find(id);
        conversation.sendMessage(sender,text,sendOn);
        repository.save(conversation);
        return conversation;
    }

    void endConversation(ConversationId id){
        log.info("Conversation {} stopped", id.id());
        repository.remove(id);
    }
}
