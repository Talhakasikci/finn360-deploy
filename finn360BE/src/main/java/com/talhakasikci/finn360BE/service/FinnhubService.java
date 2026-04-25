package com.talhakasikci.finn360BE.service;

import com.talhakasikci.finn360BE.dto.stock.FinnhubQuoteDTO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FinnhubService {
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${finnhub.api.key}")
    private String apiKey;

    public FinnhubQuoteDTO getQuote(String symbol) {
        String url = "https://finnhub.io/api/v1/quote?symbol=" + symbol + "&token=" + apiKey;
        try {
            return restTemplate.getForObject(url, FinnhubQuoteDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
