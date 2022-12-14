package pl.futurecollars.invoicing.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Company {

  @ApiModelProperty(value = "Company id", required = true, example = "54")
  private long id;

  @ApiModelProperty(value = "Tax Identification Number", required = true, example = "443-112-33-11")
  private String taxIdentificationNumber;

  @ApiModelProperty(value = "Company address", required = true, example = "ul. Jana Pawła II 21/37, 34-100 Wadowice")
  private String address;

  @ApiModelProperty(value = "Company name", required = true, example = "Manufaktura Faktur sp. z o.o.")
  private String name;
}
