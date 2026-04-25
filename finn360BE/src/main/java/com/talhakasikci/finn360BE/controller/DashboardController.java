package com.talhakasikci.finn360BE.controller;

import com.talhakasikci.finn360BE.dto.coingecko.PriceResponseDTO;
import com.talhakasikci.finn360BE.dto.dashboard.DashboardHeaderDTO;
import com.talhakasikci.finn360BE.dto.dashboard.DashboardResponseDTO;
import com.talhakasikci.finn360BE.dto.dashboard.MarketSummaryDTO;
import com.talhakasikci.finn360BE.model.User;
import com.talhakasikci.finn360BE.repository.UserRepository;
import com.talhakasikci.finn360BE.security.AuthenticatedUser;
import com.talhakasikci.finn360BE.service.CoinGeckoService;
import com.talhakasikci.finn360BE.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private CoinGeckoService coinGeckoService;

    @Autowired
    private StockService stockService; // Injecting StockService (Yeni Servisi Çağırdık)

    @Autowired
    private UserRepository userRepository; // Veritabanına erişmek için

    /**
     * GET DASHBOARD DATA
     * Endpoint: GET /api/dashboard/market-summary?userId=1
     **/
    @GetMapping("/market-summary")
    public ResponseEntity<DashboardResponseDTO> getDashboard() {

        // --- 1. DATABASE: GET USER NAME ---
        // Veritabanından ID'ye göre kullanıcıyı bul, yoksa "User" yaz.

        Authentication authentication = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (!(principal instanceof AuthenticatedUser)) {
            return ResponseEntity.status(403).build();
        }

        AuthenticatedUser authenticatedUser = (AuthenticatedUser) principal;
        String userIdFromToken = authenticatedUser.getUserId();

        String userName = userRepository.findById(userIdFromToken)
                .map(User::getName)
                .orElse("User");

        // --- 2. HEADER DATA (Dynamic) ---
        String greeting = getGreetingMessage();
        String formattedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd, EEEE", Locale.ENGLISH));

        DashboardHeaderDTO header = DashboardHeaderDTO.builder()
                .greeting(greeting)
                .userName(userName) // Artık veritabanından geliyor
                .date(formattedDate)
                .build();

        // --- 3. MARKET SUMMARY LIST (Existing Logic) ---
        List<MarketSummaryDTO> summaryList = new ArrayList<>();

        // Add Stocks
        if (stockService.getStockMarketData() != null) {
            summaryList.addAll(stockService.getStockMarketData());
        }

        // Add Crypto
        List<String> cryptoIds = List.of("bitcoin");
        Map<String, PriceResponseDTO> cryptoData = coinGeckoService.getBatchPrices(cryptoIds);

        if (cryptoData != null) {
            cryptoData.forEach((coinId, data) -> {
                String symbol = "BTC";
                String name = "Bitcoin";

                summaryList.add(MarketSummaryDTO.builder()
                        .name(name)
                        .symbol(symbol)
                        .price(data.getPriceInUsd())
                        .changePercentage(data.getChange24h())
                        .iconUrl("https://assets.coingecko.com/coins/images/1/small/bitcoin.png")
                        .build());
            });
        }

        // --- 4. COMBINE AND RETURN ---
        DashboardResponseDTO responseDTO = DashboardResponseDTO.builder()
                .header(header)
                .marketSummary(summaryList)
                .build();
        return status(200).body(responseDTO);
    }

    // Helper Method: Greeting logic
    private String getGreetingMessage() {
        int hour = LocalTime.now().getHour();
        if (hour >= 5 && hour < 12) return "Good Morning,";
        else if (hour >= 12 && hour < 17) return "Good Afternoon,";
        else if (hour >= 17 && hour < 22) return "Good Evening,";
        else return "Hello,";
    }
}