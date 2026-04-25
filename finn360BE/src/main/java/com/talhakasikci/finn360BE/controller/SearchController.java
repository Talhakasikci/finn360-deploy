package com.talhakasikci.finn360BE.controller;

import com.talhakasikci.finn360BE.dto.search.SearchResultDTO;
import com.talhakasikci.finn360BE.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    private StockService stockService;

    /**
     * Search for assets by symbol or name.
     * Endpoint: GET /api/search?query=apple
     */
    @GetMapping
    public List<SearchResultDTO> search(@RequestParam String query) {
        // Validate input: Return empty list if query is null or empty
        if (query == null || query.trim().isEmpty()) {
            return List.of();
        }

        // Delegate search logic to service layer
        return stockService.searchAssets(query);
    }
}