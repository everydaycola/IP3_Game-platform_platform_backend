package be.kdg.ipj3.platformbackend.chatbot.domain;

import be.kdg.ipj3.platformbackend.shared.domain.UserId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Slf4j
public class Conversation {
    private final ConversationId id;
    private final UserId user;
    private final List<ChatMessage> messages;

    public static Conversation startNew(UserId user) {
        return new Conversation(
                new ConversationId(),
                user,
                new ArrayList<>()
        );
    }

    public static Conversation startNewWithoutUser() {
        return new Conversation(
                new ConversationId(),
                null,
                new ArrayList<>()
        );
    }

    public void sendMessage(UUID sender, String text, LocalDateTime sentTime) {
        ChatMessage message = new ChatMessage(new ChatMessageId(), sender,text,sentTime);
        this.messages.add(message);
        log.info("Message {} added to conversation {}", message.getId().id(), this.id.id());
    }
}
