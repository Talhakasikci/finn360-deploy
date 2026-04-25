package com.talhakasikci.finn360BE.service;

import com.talhakasikci.finn360BE.dto.dashboard.MarketSummaryDTO;
import com.talhakasikci.finn360BE.dto.search.SearchResultDTO;
import com.talhakasikci.finn360BE.dto.stock.FinnhubQuoteDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class StockService {

    private final WebClient webClient;
    private final String apiKey;

    public StockService(WebClient.Builder webClientBuilder, @Value("${finnhub.api.key}") String apiKey) {
        // Finnhub Base URL
        this.webClient = webClientBuilder.baseUrl("https://finnhub.io/api/v1").build();
        this.apiKey = apiKey;
    }

    public List<MarketSummaryDTO> getStockMarketData() {
        List<MarketSummaryDTO> stocks = new ArrayList<>();
        // Target Stocks: QQQ (Nasdaq-100 ETF)
        List<String> symbols = List.of("QQQ");

        for (String symbol : symbols) {
            try {
                FinnhubQuoteDTO response = fetchQuote(symbol);
                if (response != null) {
                    // Logo mapping logic...
                    String iconUrl = "";
                    if (symbol.equals("QQQ")) iconUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/8/87/NASDAQ_Logo.svg/1200px-NASDAQ_Logo.svg.png";

                    stocks.add(MarketSummaryDTO.builder()
                            .name("Nasdaq - 100")
                            .symbol(symbol)
                            .price(response.getCurrentPrice())
                            .changePercentage(response.getPercentChange())
                            .iconUrl(iconUrl)
                            .build());
                }
            } catch (Exception e) {
                System.err.println("Error fetching dashboard data: " + e.getMessage());
            }
        }
        return stocks;
    }

    public List<SearchResultDTO> searchAssets(String query) {
        List<SearchResultDTO> results = new ArrayList<>();


        System.out.println(">>> ARAMA BAŞLADI: " + query);

        try {
            Map response = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/search")
                            .queryParam("q", query)
                            .queryParam("exchange", "US")
                            .queryParam("token", apiKey)
                            .build())
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            if (response != null && response.containsKey("result")) {
                List<Map<String, Object>> rawResults = (List<Map<String, Object>>) response.get("result");

                for (Map<String, Object> item : rawResults) {

                    if (results.size() >= 5) break;

                    String symbol = (String) item.get("symbol");
                    String desc = (String) item.get("description");
                    String type = (String) item.get("type");

                    if (symbol == null) continue;

                    boolean isCleanSymbol = !symbol.contains(".") && !symbol.contains(":") && !symbol.contains("-");
                    boolean isCommonStock = type != null && "Common Stock".equalsIgnoreCase(type);

                    if (isCleanSymbol && isCommonStock) {
                        String stockIconUrl = "https://financialmodelingprep.com/image-stock/" + symbol.toUpperCase() + ".png";
                        results.add(SearchResultDTO.builder()
                                .id(null)
                                .symbol(symbol)
                                .description(desc)
                                .type("STOCK")
                                .iconUrl(stockIconUrl)
                                .build());
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Stock search error: " + e.getMessage());
        }

//         2. Search Crypto (CoinGecko)
        try {
            WebClient cryptoClient = WebClient.create("https://api.coingecko.com/api/v3");
            Map cryptoResponse = cryptoClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/search")
                            .queryParam("query", query)
                            .build())
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            if (cryptoResponse != null && cryptoResponse.containsKey("coins")) {
                List<Map<String, Object>> coins = (List<Map<String, Object>>) cryptoResponse.get("coins");
                int limit = 0;
                for (Map<String, Object> coin : coins) {
                    if (limit++ > 5) break;
                    String symbol = (String) coin.get("symbol");
                    String name = (String) coin.get("name");
                    String id = (String) coin.get("id");
                    String iconUrl = (String) coin.get("large");

                    results.add(SearchResultDTO.builder()
                            .id(id)
                            .symbol(symbol.toUpperCase())
                            .description(name)
                            .type("CRYPTO")
                            .iconUrl(iconUrl)
                            .build());
                }
            }
        } catch (Exception e) {
            System.err.println("Crypto search error: " + e.getMessage());
        }

        return results;
    }

    // --- 3. LIVE PRICE FOR PORTFOLIO ---
    public BigDecimal getLivePrice(String symbol) {
        try {
            FinnhubQuoteDTO response = fetchQuote(symbol);
            if (response != null && response.getCurrentPrice() != null) {
                return response.getCurrentPrice();
            }
        } catch (Exception e) {
            System.err.println("Error fetching live price for " + symbol + ": " + e.getMessage());
        }
        return BigDecimal.ZERO;
    }

    // Helper Method
    private FinnhubQuoteDTO fetchQuote(String symbol) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/quote")
                        .queryParam("symbol", symbol)
//                        .queryParam("exchange", "US")
                        .queryParam("token", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(FinnhubQuoteDTO.class)
                .block();
    }
}