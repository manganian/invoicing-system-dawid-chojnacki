package pl.futurecollars.invoicing.model;

import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InvoiceEntry {

  @ApiModelProperty(value = "Product/service description", required = true, example = "Lego 21309 Saturn V")
  private String description;

  @ApiModelProperty(value = "Number of items", required = true, example = "9")
  private int quantity;

  @ApiModelProperty(value = "Product/service net price", required = true, example = "425.09")
  private BigDecimal price;

  @ApiModelProperty(value = "Product/service tax value", required = true, example = "97.77")
  private BigDecimal vatValue;

  @ApiModelProperty(value = "Tax rate", required = true)
  private Vat vatRate;

  public InvoiceEntry(String description, int quantity, BigDecimal price, BigDecimal vatValue, Vat vatRate) {
    this.description = description;
    this.quantity = quantity;
    this.price = price;
    this.vatValue = vatValue;
    this.vatRate = vatRate;
  }
}
