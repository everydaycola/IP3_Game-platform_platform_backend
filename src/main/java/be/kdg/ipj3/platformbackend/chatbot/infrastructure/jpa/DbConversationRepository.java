package be.kdg.ipj3.platformbackend.chatbot.infrastructure.jpa;

import be.kdg.ipj3.platformbackend.chatbot.domain.Conversation;
import be.kdg.ipj3.platformbackend.chatbot.domain.ConversationId;
import be.kdg.ipj3.platformbackend.chatbot.domain.repository.ConversationRepository;
import be.kdg.ipj3.platformbackend.chatbot.infrastructure.jpa.entity.JpaConversationEntity;
import be.kdg.ipj3.platformbackend.chatbot.infrastructure.jpa.repository.JpaConversationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
public class DbConversationRepository implements ConversationRepository {

    private final JpaConversationRepository jpaConversationRepository;

    public DbConversationRepository(JpaConversationRepository jpaConversationRepository) {
        this.jpaConversationRepository = jpaConversationRepository;
    }

    @Override
    public void save(Conversation conversation) {
        jpaConversationRepository.save(JpaConversationEntity.fromDomain(conversation));
    }

    @Override
    public Optional<Conversation> findById(ConversationId id) {
        return jpaConversationRepository.findByIdWithMessages(id.id()).map(JpaConversationEntity::toDomain);
    }
}
