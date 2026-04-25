package com.talhakasikci.finn360BE.dto.dashboard;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardHeaderDTO {
    private String greeting;
    private String userName;
    private String date;
}