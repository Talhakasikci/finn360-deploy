
package com.talhakasikci.finn360BE.dto.stock;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class FinnhubQuoteDTO {

    @JsonProperty("c") // Maps JSON property "c" (Current Price) to this field ("c" harfini currentPrice'a eşledik)
    private BigDecimal currentPrice;

    @JsonProperty("dp") // Maps JSON property "dp" (Percent change) to this field ("dp" harfini percentChange'e eşledik)
    private BigDecimal percentChange;
}