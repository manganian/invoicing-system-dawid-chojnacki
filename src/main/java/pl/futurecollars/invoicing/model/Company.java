package pl.futurecollars.invoicing.model;

import lombok.Data;

@Data
public class Company {

  private long id;
  private String taxIdentificationNumber;
  private String address;
  private String name;

  public Company(String taxIdentificationNumber, String address, String name) {
    this.taxIdentificationNumber = taxIdentificationNumber;
    this.address = address;
    this.name = name;
  }
}
