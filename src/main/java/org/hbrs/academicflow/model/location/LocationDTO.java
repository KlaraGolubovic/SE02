package org.hbrs.academicflow.model.location;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationDTO {

  private String street;
  private String houseNumber;
  private String city;
  private String zipCode;
  private String country;

  public String toFormattedString() {
    return String.format(
        "%s. %s, %s %s %s", this.street, this.houseNumber, this.zipCode, this.city, this.country);
  }
}
