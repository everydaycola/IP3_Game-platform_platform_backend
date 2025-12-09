package be.kdg.ipj3.platformbackend.analytics.api;

import be.kdg.ipj3.platformbackend.analytics.events.GameEndedEvent;
import be.kdg.ipj3.platformbackend.analytics.events.GameStartedEvent;
import be.kdg.ipj3.platformbackend.analytics.events.PurchaseMadeEvent;
import be.kdg.ipj3.platformbackend.analytics.infrastructure.AnalyticsEventPublisher;
import be.kdg.ipj3.platformbackend.shared.domain.UserId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

/**
 * Test controller for manually triggering analytics events
 * This should be removed or secured in production
 */
@Slf4j
@RestController
@RequestMapping("/api/analytics/test")
public class AnalyticsTestController {

    private final AnalyticsEventPublisher analyticsEventPublisher;

    public AnalyticsTestController(AnalyticsEventPublisher analyticsEventPublisher) {
        this.analyticsEventPublisher = analyticsEventPublisher;
    }

    @PostMapping("/game-started")
    public ResponseEntity<Map<String, String>> testGameStarted(
            @RequestParam String gameId,
            @RequestParam String gameName,
            @AuthenticationPrincipal Jwt token) {

        UserId userId = UserId.fromToken(token);
        String sessionId = UUID.randomUUID().toString();

        GameStartedEvent event = new GameStartedEvent(
            gameId,
            userId.id().toString(),
            sessionId,
            gameName,
            2,
            Instant.now().toString()
        );

        analyticsEventPublisher.publishGameStartedEvent(event);

        return ResponseEntity.ok(Map.of(
            "status", "success",
            "message", "Game started event published",
            "sessionId", sessionId
        ));
    }

    @PostMapping("/game-ended")
    public ResponseEntity<Map<String, String>> testGameEnded(
            @RequestParam String gameId,
            @RequestParam String sessionId,
            @RequestParam(defaultValue = "1800") Integer duration,
            @RequestParam(defaultValue = "true") Boolean completed,
            @AuthenticationPrincipal Jwt token) {

        UserId userId = UserId.fromToken(token);

        GameEndedEvent event = new GameEndedEvent(
            gameId,
            userId.id().toString(),
            sessionId,
            duration,
            completed,
            Instant.now().toString()
        );

        analyticsEventPublisher.publishGameEndedEvent(event);

        return ResponseEntity.ok(Map.of(
            "status", "success",
            "message", "Game ended event published"
        ));
    }

    @PostMapping("/purchase")
    public ResponseEntity<Map<String, String>> testPurchase(
            @RequestParam String gameId,
            @RequestParam(defaultValue = "15.99") String amount,
            @AuthenticationPrincipal Jwt token) {

        UserId userId = UserId.fromToken(token);
        String transactionId = UUID.randomUUID().toString();

        PurchaseMadeEvent event = new PurchaseMadeEvent(
            userId.id().toString(),
            gameId,
            "game",
            new BigDecimal(amount),
            "EUR",
            transactionId
        );

        analyticsEventPublisher.publishPurchaseMadeEvent(event);

        return ResponseEntity.ok(Map.of(
            "status", "success",
            "message", "Purchase event published",
            "transactionId", transactionId
        ));
    }
}

