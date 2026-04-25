package com.talhakasikci.finn360BE.dto.dashboard;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class DashboardResponseDTO {
    private DashboardHeaderDTO header;            // Top section
    private List<MarketSummaryDTO> marketSummary; // Bottom section (The list)
}