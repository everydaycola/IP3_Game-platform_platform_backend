package be.kdg.ipj3.platformbackend.chatbot.application;

import be.kdg.ipj3.platformbackend.chatbot.domain.Conversation;
import be.kdg.ipj3.platformbackend.chatbot.domain.ConversationId;
import be.kdg.ipj3.platformbackend.chatbot.domain.repository.ConversationRepository;
import be.kdg.ipj3.platformbackend.shared.domain.UserId;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
@Transactional
public class ConversationService {
    private final ConversationRepository repository;

    public ConversationService(ConversationRepository repository) {
        this.repository = repository;
    }

    public Conversation find(ConversationId id){
        return repository.findById(id).orElseThrow(id::notFound);
    }

    public Conversation startNew(UserId userId){
        Conversation newConversation = Conversation.startNew(userId);
        log.info("New Conversation {} started for user {}", newConversation.getId().id(), userId.id());
        repository.save(newConversation);
        return newConversation;
    }

    public Conversation startNewWithoutUser(){
        Conversation newConversation = Conversation.startNewWithoutUser();
        log.info("New Conversation {} started without a user", newConversation.getId().id());
        repository.save(newConversation);
        return newConversation;
    }

    public Conversation sendMessage(ConversationId id, UUID sender, String text, LocalDateTime sendOn){
        Conversation conversation = find(id);
        conversation.sendMessage(sender,text,sendOn);
        repository.save(conversation);
        return conversation;
    }

    public void endConversation(ConversationId id){
        log.info("Conversation {} stopped", id.id());
        repository.remove(id);
    }
}
