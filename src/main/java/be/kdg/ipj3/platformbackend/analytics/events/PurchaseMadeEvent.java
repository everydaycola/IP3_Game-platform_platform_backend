package be.kdg.ipj3.platformbackend.analytics.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PurchaseMadeEvent extends BaseEvent {
    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("game_id")
    private String gameId;

    @JsonProperty("product_type")
    private String productType;

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("transaction_id")
    private String transactionId;

    public PurchaseMadeEvent(String userId, String gameId, String productType, BigDecimal amount, String currency, String transactionId) {
        super("purchase_made");
        this.userId = userId;
        this.gameId = gameId;
        this.productType = productType;
        this.amount = amount;
        this.currency = currency;
        this.transactionId = transactionId;
    }
}

