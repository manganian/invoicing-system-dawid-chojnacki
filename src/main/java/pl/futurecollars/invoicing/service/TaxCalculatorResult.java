package pl.futurecollars.invoicing.service;

import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class TaxCalculatorResult {

  @ApiModelProperty(value = "Income (calculated by application)", required = true, example = "10000")
  private final BigDecimal income;

  @ApiModelProperty(value = "Costs (calculated by application)", required = true, example = "1000")
  private final BigDecimal costs;

  @ApiModelProperty(value = "Earnings (calculated by application)", required = true, example = "9000")
  private final BigDecimal earnings;

  @ApiModelProperty(value = "Collected VAT (calculated by application)", required = true, example = "800")
  private final BigDecimal incomingVat;

  @ApiModelProperty(value = "Payed VAT (calculated by application)", required = true, example = "80")
  private final BigDecimal outgoingVat;

  @ApiModelProperty(value = "Outstanding VAT (calculated by application)", required = true, example = "720")
  private final BigDecimal vatToReturn;
}
