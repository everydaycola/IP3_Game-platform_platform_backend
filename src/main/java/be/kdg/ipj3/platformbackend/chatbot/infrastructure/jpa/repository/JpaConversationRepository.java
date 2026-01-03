package be.kdg.ipj3.platformbackend.chatbot.infrastructure.jpa.repository;

import be.kdg.ipj3.platformbackend.chatbot.infrastructure.jpa.entity.JpaConversationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface JpaConversationRepository extends JpaRepository<JpaConversationEntity, UUID> {

    @Query("""
SELECT c FROM JpaConversationEntity c
JOIN c.messages
WHERE c.id = :id
""")
    Optional<JpaConversationEntity> findByIdWithMessages(UUID id);

    void removeById(UUID id);
}
