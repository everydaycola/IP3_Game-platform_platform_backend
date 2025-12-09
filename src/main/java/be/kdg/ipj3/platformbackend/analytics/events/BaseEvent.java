package be.kdg.ipj3.platformbackend.analytics.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
public abstract class BaseEvent {
    @JsonProperty("event_type")
    private String eventType;

    @JsonProperty("timestamp")
    private String timestamp;

    protected BaseEvent(String eventType) {
        this.eventType = eventType;
        this.timestamp = Instant.now().toString();
    }
}

