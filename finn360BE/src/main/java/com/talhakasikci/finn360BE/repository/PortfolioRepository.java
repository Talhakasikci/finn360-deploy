package com.talhakasikci.finn360BE.repository;

import com.talhakasikci.finn360BE.model.PortfolioItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface PortfolioRepository extends MongoRepository<PortfolioItem, String> {

    // Find all assets belonging to a specific user
    List<PortfolioItem> findByUserId(String userId);

    // Delete a specific asset from user's portfolio
    void deleteByUserIdAndSymbol(String userId, String symbol);

    // Check if asset already exists in portfolio
    boolean existsByUserIdAndSymbol(String userId, String symbol);

    // Find specific asset (to update quantity)
    PortfolioItem findByUserIdAndSymbol(String userId, String symbol);
}