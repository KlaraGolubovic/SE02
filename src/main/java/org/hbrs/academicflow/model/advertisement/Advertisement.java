package org.hbrs.academicflow.model.advertisement;

import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hbrs.academicflow.model.common.BaseEntity;
import org.hbrs.academicflow.model.company.user.Company;
import org.hbrs.academicflow.model.location.Location;
import org.jetbrains.annotations.NotNull;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "advertisement", schema = "public")
public class Advertisement extends BaseEntity {

  @NotNull
  @Builder.Default
  @Column(name = "title", nullable = false)
  private String title = "";

  @NotNull
  @Builder.Default
  @Column(name = "deadline")
  private Instant deadline = Instant.now();

  @NotNull
  @Builder.Default
  @Column(name = "description", columnDefinition = "text", length = 1000000)
  private String description = "";

  @NotNull
  @Builder.Default
  @Column(name = "job_type", nullable = false)
  private String jobType = "";

  @NotNull
  @ManyToOne(optional = false)
  @JoinColumn(
      name = "location_id",
      referencedColumnName = "id",
      foreignKey = @ForeignKey(name = "fk_location_id"))
  private Location location;

  @NotNull
  @Builder.Default
  @Column(name = "remote", nullable = false)
  private Boolean remote = false;

  @NotNull
  @ManyToOne(optional = false)
  @JoinColumn(
      name = "company_id",
      referencedColumnName = "id",
      foreignKey = @ForeignKey(name = "fk_company_id"))
  private Company company;

  @NotNull
  @Builder.Default
  @Column(name = "active", nullable = false)
  private Boolean active = true;

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
    final Advertisement advertisement = (Advertisement) obj;
    return this.company.equals(advertisement.company);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + this.company.hashCode();
    return result;
  }
}
