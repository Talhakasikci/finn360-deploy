package com.talhakasikci.finn360BE.service;

import com.talhakasikci.finn360BE.dto.coingecko.PriceResponseDTO;
import com.talhakasikci.finn360BE.dto.portfolio.PortfolioOverviewDTO;
import com.talhakasikci.finn360BE.dto.portfolio.PortfolioResponseDTO;
import com.talhakasikci.finn360BE.dto.stock.FinnhubQuoteDTO;
import com.talhakasikci.finn360BE.model.PortfolioItem;
import com.talhakasikci.finn360BE.repository.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PortfolioService {

    @Autowired
    private PortfolioRepository repository;

    @Autowired
    private CoinGeckoService coinGeckoService;

    @Autowired
    private FinnhubService finnhubService;


    public String addOrUpdateAsset(PortfolioItem item) {

        // Veritabanında bu kullanıcıya ait bu sembol var mı?
        PortfolioItem existingItem = repository.findByUserIdAndSymbol(item.getUserId(), item.getSymbol());

        if (existingItem != null) {
            // VARSA: Üzerine ekleme yap
            BigDecimal newQuantity = existingItem.getQuantity().add(item.getQuantity());
            existingItem.setQuantity(newQuantity);

            // Eğer yeni bir maliyet bilgisi girildiyse onu da güncelle (Basit güncelleme)
            // İstersen burada ağırlıklı ortalama maliyet hesabı da yapabilirsin.
            if (item.getAverageBuyPrice() != null) {
                existingItem.setAverageBuyPrice(item.getAverageBuyPrice());
            }

            repository.save(existingItem);
            return "Updated portfolio: " + item.getSymbol();

        } else {
            // YOKSA: Yeni kayıt oluştur ve Logo URL belirle
            if ("STOCK".equalsIgnoreCase(item.getAssetType())) {
                String staticLogoUrl = "https://financialmodelingprep.com/image-stock/" + item.getSymbol().toUpperCase() + ".png";
                item.setIconUrl(staticLogoUrl);

            } else if ("CRYPTO".equalsIgnoreCase(item.getAssetType())) {
                String staticLogoUrl = "https://assets.coincap.io/assets/icons/" + item.getSymbol().toLowerCase() + "@2x.png";
                item.setIconUrl(staticLogoUrl);
            }

            // Yeni öğeyi kaydet
            repository.save(item);
            return "Added to portfolio: " + item.getSymbol();
        }
    }


    public PortfolioOverviewDTO getPortfolioOverview(String userId) {
        // 1. Kullanıcının varlıklarını çek
        List<PortfolioItem> dbItems = repository.findByUserId(userId);

        // 2. Kripto ID'lerini topla
        List<String> cryptoIds = dbItems.stream()
                .filter(item -> "CRYPTO".equalsIgnoreCase(item.getAssetType()) && item.getInstrumentId() != null)
                .map(PortfolioItem::getInstrumentId)
                .collect(Collectors.toList());

        // 3. Fiyatları çek
        Map<String, PriceResponseDTO> cryptoPrices = coinGeckoService.getBatchPrices(cryptoIds);

        List<PortfolioResponseDTO> dtoList = new ArrayList<>();

        // GENEL TOPLAM HESAPLAYICILAR
        BigDecimal globalTotalValue = BigDecimal.ZERO; // Toplam Portföy Değeri
        BigDecimal globalTotalCost = BigDecimal.ZERO;  // Toplam Harcanan Para (Maliyet)

        // 4. Döngü ile tek tek hesapla
        for (PortfolioItem item : dbItems) {
            BigDecimal currentPrice = BigDecimal.ZERO;
            BigDecimal changePercent24h = BigDecimal.ZERO;

            if ("CRYPTO".equalsIgnoreCase(item.getAssetType())) {
                if (item.getInstrumentId() != null && cryptoPrices.containsKey(item.getInstrumentId())) {
                    PriceResponseDTO p = cryptoPrices.get(item.getInstrumentId());
                    currentPrice = p.getPriceInUsd();
                    changePercent24h = p.getChange24h();
                }
            } else if ("STOCK".equalsIgnoreCase(item.getAssetType())) {
                FinnhubQuoteDTO stockData = finnhubService.getQuote(item.getSymbol());
                if (stockData != null) {
                    currentPrice = stockData.getCurrentPrice();
                    changePercent24h = stockData.getPercentChange();
                }
            }

            // Item Bazlı Hesaplamalar
            BigDecimal avgPrice = item.getAverageBuyPrice() != null ? item.getAverageBuyPrice() : BigDecimal.ZERO;
            BigDecimal itemTotalValue = currentPrice.multiply(item.getQuantity());
            BigDecimal itemTotalCost = avgPrice.multiply(item.getQuantity());
            BigDecimal itemProfitLoss = itemTotalValue.subtract(itemTotalCost);

            BigDecimal itemProfitLossPercentage = BigDecimal.ZERO;
            if (avgPrice.compareTo(BigDecimal.ZERO) > 0) {
                itemProfitLossPercentage = currentPrice.subtract(avgPrice)
                        .divide(avgPrice, 4, RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(100));
            }

            // DTO Listesine Ekle
            dtoList.add(PortfolioResponseDTO.builder()
                    .id(item.getId())
                    .symbol(item.getSymbol())
                    .description(item.getDescription())
                    .iconUrl(item.getIconUrl())
                    .assetType(item.getAssetType())
                    .quantity(item.getQuantity())
                    .averagePrice(avgPrice)
                    .currentPrice(currentPrice)
                    .totalValue(itemTotalValue)
                    .profitLoss(itemProfitLoss)
                    .profitLossPercentage(itemProfitLossPercentage)
                    .changePercentage(changePercent24h)
                    .build());

            // GENEL TOPLAMLARI GÜNCELLE
            globalTotalValue = globalTotalValue.add(itemTotalValue);
            globalTotalCost = globalTotalCost.add(itemTotalCost);
        }

        // 5. GENEL KAR/ZARAR HESAPLA
        BigDecimal globalProfitLoss = globalTotalValue.subtract(globalTotalCost);

        BigDecimal globalProfitLossPercentage = BigDecimal.ZERO;
        if (globalTotalCost.compareTo(BigDecimal.ZERO) > 0) {
            // (Toplam Değer - Toplam Maliyet) / Toplam Maliyet * 100
            globalProfitLossPercentage = globalProfitLoss
                    .divide(globalTotalCost, 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
        }

        return PortfolioOverviewDTO.builder()
                .totalBalance(globalTotalValue)
                .totalProfitLoss(globalProfitLoss)
                .totalProfitLossPercentage(globalProfitLossPercentage)
                .items(dtoList)
                .build();
    }
}