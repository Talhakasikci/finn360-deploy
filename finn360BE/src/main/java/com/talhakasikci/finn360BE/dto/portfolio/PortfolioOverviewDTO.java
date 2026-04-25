package com.talhakasikci.finn360BE.dto.portfolio;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class PortfolioOverviewDTO {

    private BigDecimal totalBalance;
    private BigDecimal totalProfitLoss;
    private BigDecimal totalProfitLossPercentage;

    private List<PortfolioResponseDTO> items;
}