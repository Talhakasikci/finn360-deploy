package com.talhakasikci.finn360BE.controller;

import com.talhakasikci.finn360BE.dto.portfolio.PortfolioOverviewDTO;
import com.talhakasikci.finn360BE.dto.portfolio.PortfolioResponseDTO;
import com.talhakasikci.finn360BE.model.PortfolioItem;
import com.talhakasikci.finn360BE.repository.PortfolioRepository;
import com.talhakasikci.finn360BE.security.AuthenticatedUser;
import com.talhakasikci.finn360BE.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/portfolio")
public class PortfolioController {

    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private PortfolioRepository repository;

    // LIST: GET /api/portfolio
    @GetMapping
    public ResponseEntity<PortfolioOverviewDTO> getPortfolio() {
        String userId = getAuthenticatedUserId();

        PortfolioOverviewDTO overview = portfolioService.getPortfolioOverview(userId);

        return ResponseEntity.ok(overview);
    }

    // ADD: POST /api/portfolio
    @PostMapping
    public String addInvestment(@RequestBody PortfolioItem item) {
        String userId = getAuthenticatedUserId();
        item.setUserId(userId); // ID Token'dan zorla set edilir

        return portfolioService.addOrUpdateAsset(item);
    }

    // DELETE: DELETE /api/portfolio
    @DeleteMapping
    public String deleteInvestment(@RequestParam String symbol) {
        String userId = getAuthenticatedUserId();
        repository.deleteByUserIdAndSymbol(userId, symbol);
        return "Removed from portfolio: " + symbol;
    }



    private String getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof AuthenticatedUser) {
            return ((AuthenticatedUser) principal).getUserId();
        }
        throw new RuntimeException("Unauthorized access");
    }
}