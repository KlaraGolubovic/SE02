package org.hbrs.academicflow.model.company.profile;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hbrs.academicflow.model.common.BaseEntity;
import org.hbrs.academicflow.model.company.user.Company;
import org.hbrs.academicflow.model.location.Location;
import org.hbrs.academicflow.model.rating.Rating;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "company_profile", schema = "public")
public class CompanyProfile extends BaseEntity {

  @NotNull
  @OneToOne(orphanRemoval = true, optional = false)
  @JoinColumn(
      name = "company_id",
      referencedColumnName = "id",
      foreignKey = @ForeignKey(name = "fk_company_id"))
  private Company company;

  @Nullable
  @Builder.Default
  @Column(name = "description")
  private String description = "";

  @NotNull
  @OneToOne(optional = false)
  @JoinColumn(
      name = "location_id",
      referencedColumnName = "id",
      foreignKey = @ForeignKey(name = "fk_location_id"))
  private Location location;

  // CURRENTLY NOT A LOB (INDEXED IMAGES)
  // @Lob
  // @Type(type = "org.hibernate.type.ImageType")
  @NotNull
  @Builder.Default
  @Column(name = "image", nullable = false)
  private Integer image = -1;

  @OneToMany(mappedBy = "companyProfile", fetch = FetchType.EAGER)
  private Set<Rating> ratings = new HashSet<>();

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
    final CompanyProfile profile = (CompanyProfile) obj;
    return this.company.equals(profile.company);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + this.company.hashCode();
    return result;
  }

  public double getRating() {
    return this.ratings.stream().mapToInt(Rating::getValue).average().orElse(0);
  }
}