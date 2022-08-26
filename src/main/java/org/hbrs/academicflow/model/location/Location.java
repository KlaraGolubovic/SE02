package org.hbrs.academicflow.model.location;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hbrs.academicflow.model.common.BaseEntity;
import org.jetbrains.annotations.NotNull;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
    name = "location",
    schema = "public",
    indexes = {
        @Index(name = "idx_city", columnList = "city"),
        @Index(name = "idx_zip_code", columnList = "zip_code"),
    })
public class Location extends BaseEntity {

  @NotNull
  @Column(name = "street", nullable = false)
  private String street;
  @NotNull
  @Column(name = "house_number", nullable = false)
  private String houseNumber;
  @NotNull
  @Column(name = "city", nullable = false)
  private String city;
  @NotNull
  @Column(name = "zip_code", nullable = false)
  private String zipCode;
  @NotNull
  @Column(name = "country", nullable = false)
  private String country;

  public static ManualLocationBuilder manualBuilder() {
    return new ManualLocationBuilder();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || this.getClass() != obj.getClass()) {
      return false;
    }
    if (!super.equals(obj)) {
      return false;
    }
    final Location location = (Location) obj;
    if (!this.street.equals(location.street)) {
      return false;
    }
    if (!this.houseNumber.equals(location.houseNumber)) {
      return false;
    }
    if (!this.city.equals(location.city)) {
      return false;
    }
    if (!this.zipCode.equals(location.zipCode)) {
      return false;
    }
    return this.country.equals(location.country);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + this.street.hashCode();
    result = 31 * result + this.houseNumber.hashCode();
    result = 31 * result + this.city.hashCode();
    result = 31 * result + this.zipCode.hashCode();
    result = 31 * result + this.country.hashCode();
    return result;
  }
}
