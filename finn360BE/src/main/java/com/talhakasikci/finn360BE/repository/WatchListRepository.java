package com.talhakasikci.finn360BE.repository;

import com.talhakasikci.finn360BE.model.WatchListItem;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface WatchListRepository extends MongoRepository<WatchListItem, String> {

    // Find all watchlist items for a specific user (Belirli bir kullanıcının tüm listesini bul)
    List<WatchListItem> findByUserId(String UUID);
    // Find all items with DYNAMIC SORTING capability (Veritabanına "Sort" nesnesi göndererek sıralı veri çekeceğiz)
    List<WatchListItem> findByUserId(String userId, Sort sort);
    // Check if the user has already added this asset (Kullanıcı bu coini zaten eklemiş mi?)
    boolean existsByUserIdAndSymbol(String userId, String symbol);
    // Remove specific asset from the watchlist (Listeden sil)
    void deleteByUserIdAndSymbol(String userId, String symbol);

}