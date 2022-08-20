package pl.futurecollars.invoicing.model;

import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InvoiceEntry {

  private String description;
  private BigDecimal price;
  private BigDecimal vatValue;
  private Vat vatRate;

  public InvoiceEntry(String description, BigDecimal price, BigDecimal vatValue, Vat vatRate) {
    this.description = description;
    this.price = price;
    this.vatValue = vatValue;
    this.vatRate = vatRate;
  }
}
