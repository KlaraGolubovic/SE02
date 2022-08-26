package org.hbrs.academicflow.model.rating;

import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hbrs.academicflow.model.common.BaseEntity;
import org.hbrs.academicflow.model.company.profile.CompanyProfile;
import org.hbrs.academicflow.model.student.user.Student;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "rating", schema = "public")
public class Rating extends BaseEntity {

  @NotNull
  @Builder.Default
  @Column(name = "timestamp", nullable = false)
  private Instant timestamp = Instant.now();
  @Builder.Default
  @Column(name = "value", nullable = false)
  private int value = 1;
  @NotNull
  @ManyToOne(optional = false)
  @JoinColumn(
      name = "student",
      referencedColumnName = "id",
      foreignKey = @ForeignKey(name = "fk_rating_student"))
  private Student student;
  @NotNull
  @ManyToOne(optional = false)
  @JoinColumn(
      name = "companyProfile",
      referencedColumnName = "id",
      foreignKey = @ForeignKey(name = "companyProfile"))
  private CompanyProfile companyProfile;

  // Override toString for better logging
  @Override
  public String toString() {
    return "Rating{timestamp="
        + timestamp
        + ", value="
        + value
        + ", student="
        + student
        + ", companyProfile="
        + companyProfile
        + '}';
  }
}