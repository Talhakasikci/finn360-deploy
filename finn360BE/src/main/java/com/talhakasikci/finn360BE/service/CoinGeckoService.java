package com.talhakasikci.finn360BE.service;

import com.talhakasikci.finn360BE.dto.coingecko.PriceResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class CoinGeckoService {

    private final WebClient webClient;
    private final String currency;

    public CoinGeckoService(WebClient.Builder webClientBuilder,
                            @Value("${coingecko.api.base-url}") String baseUrl,
                            @Value("${coingecko.api.currency}") String currency,
                            @Value("${coingecko.api.key}") String apiKey){

        if (apiKey == null || apiKey.isEmpty()) {
            log.error("KRİTİK HATA: API Key application.properties dosyasından okunamadı! Boş geliyor.");
        } else {
            log.info("API Key Başarıyla Okundu: {}...", apiKey.substring(0, Math.min(apiKey.length(), 4)));
        }

        this.webClient = webClientBuilder.
                baseUrl(baseUrl)
                .defaultHeader("x-cg-demo-api-key",apiKey).build();
        this.currency = currency;
    }

    @Cacheable(value = "cryptoPrices", key = "#coinIds.toString()", unless = "#result == null || #result.isEmpty()")
    public Map<String, PriceResponseDTO> getBatchPrices(List<String> coinIds) {
        if (coinIds == null || coinIds.isEmpty()) {
            return Collections.emptyMap();
        }

        String idsParam = String.join(",", coinIds);

        try {
            Map<String, PriceResponseDTO> response = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/simple/price")
                            .queryParam("ids", idsParam)
                            .queryParam("vs_currencies", this.currency)
                            .queryParam("include_24hr_change", true)
                            .build())
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Map<String, PriceResponseDTO>>() {})
                    .block();

            return response != null ? response: Collections.emptyMap();
        } catch (WebClientResponseException e) {
            //status 4xx or 5xx
            //log.error("CoinGecko API Hatası: Status Code: {}, Body: {}", e.getStatusCode(), e.getResponseBodyAsString());
            return Collections.emptyMap();
        }catch (Exception e) {
            //connection errors or unexpected errors
            log.error("CoinGecko Beklenmeyen Hata: ", e);
            return Collections.emptyMap();
        }


    }
}