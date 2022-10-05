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

  @ApiModelProperty(value = "incomeMinusCosts (calculated by application)", required = true, example = "720")
  private final BigDecimal incomeMinusCosts;

  @ApiModelProperty(value = "pensionInsurance (calculated by application)", required = true, example = "720")
  private final BigDecimal pensionInsurance;

  @ApiModelProperty(value = "incomeMinusCostsMinusPensionInsurance (calculated by application)", required = true, example = "500")
  private final BigDecimal incomeMinusCostsMinusPensionInsurance;

  @ApiModelProperty(value = "incomeMinusCostsMinusPensionInsuranceRounded (calculated by application)", required = true, example = "400")
  private final BigDecimal incomeMinusCostsMinusPensionInsuranceRounded;

  @ApiModelProperty(value = "incomeTax (calculated by application)", required = true, example = "5500")
  private final BigDecimal incomeTax;

  @ApiModelProperty(value = "healthInsurancePaid (calculated by application)", required = true, example = "85")
  private final BigDecimal healthInsurancePaid;

  @ApiModelProperty(value = "healthInsuranceToSubtract (calculated by application)", required = true, example = "854")
  private final BigDecimal healthInsuranceToSubtract;

  @ApiModelProperty(value = "incomeTaxMinusHealthInsurance (calculated by application)", required = true, example = "851")
  private final BigDecimal incomeTaxMinusHealthInsurance;

  @ApiModelProperty(value = "healthInsuranceToSubtract (calculated by application)", required = true, example = "458")
  private final BigDecimal finalIncomeTax;


}
