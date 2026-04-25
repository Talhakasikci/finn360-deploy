package com.talhakasikci.finn360BE.controller;

import ch.qos.logback.core.read.ListAppender;
import com.talhakasikci.finn360BE.dto.watchlist.WatclistResponseDTO;
import com.talhakasikci.finn360BE.model.WatchListItem;
import com.talhakasikci.finn360BE.repository.WatchListRepository;
import com.talhakasikci.finn360BE.security.AuthenticatedUser;
import com.talhakasikci.finn360BE.service.WatchListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/watchlist")
public class WatchListController {

    @Autowired
    private WatchListRepository repository;

    @Autowired
    private WatchListService watchListService;

    /**
     * LIST WATCHLIST (GET)
     * Supports sorting parameters.
     * Default behavior: Alphabetical sort by Symbol (A-Z).
     *
     * Example usage from Frontend:
     * 1. Default (A-Z): GET /api/watchlist?userId=1
     * 2. Newest First:  GET /api/watchlist?userId=1&sortBy=id&direction=desc
     **/

    // LIST: GET /api/watchlist?userId=1
    @GetMapping
    public ResponseEntity<List<WatclistResponseDTO>> getWatchList(
            @RequestParam(defaultValue = "symbol") String sortBy, // Default: Alphabetical
            @RequestParam(defaultValue = "asc") String direction  // Default: A-Z
    ) {
            String userId = getAuthenticatedUserId();

            if (userId == null) {
                return ResponseEntity.status(403).build();
            } else {
                // Determine sorting direction
                Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
                // Create Sort object
                Sort sort = Sort.by(sortDirection, sortBy);
                List<WatclistResponseDTO> list = watchListService.getUserWatchListWithPrices(userId,sort);

                return ResponseEntity.ok(list);
            }
    }

    // ADD: POST /api/watchlist
    @PostMapping
    public String addToWatchList(@RequestBody WatchListItem item) {

        String userId = getAuthenticatedUserId();
        item.setUserId(userId);
        // Check if already exists
        if (repository.existsByUserIdAndSymbol(userId, item.getSymbol())) {
            return "This asset is already in your watchlist.";
        }
        return watchListService.addAssetToWatchList(item);
    }

    // REMOVE: DELETE /api/watchlist?userId=1&symbol=BTC
    @DeleteMapping
    public String removeFromWatchList(@RequestParam String symbol) {
        String userId = getAuthenticatedUserId();

        repository.deleteByUserIdAndSymbol(userId, symbol.toUpperCase());
        return "Removed from watchlist: " + symbol;
    }

    private String getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Object principal = authentication.getPrincipal();

        if (principal instanceof AuthenticatedUser) {
            AuthenticatedUser authenticatedUser = (AuthenticatedUser) principal;
            return authenticatedUser.getUserId();
        } else {
            throw new RuntimeException("Unauthorized access");
        }
    }
}