package com.talhakasikci.finn360BE.service;

import com.talhakasikci.finn360BE.dto.coingecko.PriceResponseDTO;
import com.talhakasikci.finn360BE.dto.stock.FinnhubQuoteDTO;
import com.talhakasikci.finn360BE.dto.watchlist.WatclistResponseDTO;
import com.talhakasikci.finn360BE.model.WatchListItem;
import com.talhakasikci.finn360BE.repository.WatchListRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WatchListService {

    @Autowired private WatchListRepository repository;
    @Autowired private CoinGeckoService coinGeckoService;
    @Autowired private FinnhubService finnhubService;


    public String addAssetToWatchList(WatchListItem item) {

        if (repository.existsByUserIdAndSymbol(item.getUserId(), item.getSymbol())) {
            throw new RuntimeException("Asset already in watchlist.");

        }

        if ("STOCK".equalsIgnoreCase(item.getAssetType())) {
            String staticLogoUrl = "https://financialmodelingprep.com/image-stock/" + item.getSymbol().toUpperCase() + ".png";
            item.setIconUrl(staticLogoUrl);

        } else if ("CRYPTO".equalsIgnoreCase(item.getAssetType())) {
            String staticLogoUrl = "https://assets.coincap.io/assets/icons/" + item.getSymbol().toLowerCase() + "@2x.png";
            item.setIconUrl(staticLogoUrl);
        }
        repository.save(item);
        return "Asset added to watchlist.";
    }

    public List<WatclistResponseDTO> getUserWatchListWithPrices(String userId, Sort sort) {


        List<WatchListItem> dbList = repository.findByUserId(userId, sort);
        List<String> cryptoIds = dbList.stream()
                .filter(item -> "CRYPTO".equalsIgnoreCase(item.getAssetType())&&item.getInstrumentId() != null)
                .map(WatchListItem::getInstrumentId)
                .collect(Collectors.toList());

        Map<String, PriceResponseDTO> cryptoPrices = coinGeckoService.getBatchPrices(cryptoIds);

        List<WatclistResponseDTO> finalResponse = new ArrayList<>();

        for (WatchListItem item: dbList) {
            WatclistResponseDTO dto = new WatclistResponseDTO();

            dto.setId(item.getId());
            dto.setSymbol(item.getSymbol());
            dto.setInstrumentId(item.getInstrumentId());
            dto.setAssetType(item.getAssetType());
            dto.setDescription(item.getDescription());
            dto.setIconUrl(item.getIconUrl());

            if ("CRYPTO".equalsIgnoreCase(item.getAssetType())){

                if (item.getInstrumentId() != null && cryptoPrices.containsKey(item.getInstrumentId())) {
                    PriceResponseDTO priceData = cryptoPrices.get(item.getInstrumentId());
                    dto.setCurrentPrice(priceData.getPriceInUsd());
                    dto.setPriceChangePercent(priceData.getChange24h());
                }
            } else if ("STOCK".equalsIgnoreCase(item.getAssetType())) {
                FinnhubQuoteDTO stockData = finnhubService.getQuote(item.getSymbol());
                if (stockData != null) {
                    dto.setCurrentPrice(stockData.getCurrentPrice());
                    dto.setPriceChangePercent(stockData.getPercentChange());
                }
            }
            finalResponse.add(dto);
        }
        return finalResponse;
    }
}
