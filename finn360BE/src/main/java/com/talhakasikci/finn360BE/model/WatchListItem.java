package com.talhakasikci.finn360BE.model;

import com.mongodb.lang.Nullable;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "watchlist")
public class WatchListItem {

    @Id
    private String id;
    @Nullable
    private String instrumentId; //

    private String userId; // User identifier (ID is sufficient for now) (Kullanıcıyı ayırt etmek için (Şimdilik ID yeterli))

    private String symbol; // e.g., "bitcoin", "ethereum", "aapl"

    private String assetType; // "CRYPTO" or "STOCK"

    private String description; // e.g., "Bitcoin", "Apple Inc."

    private String iconUrl;
}