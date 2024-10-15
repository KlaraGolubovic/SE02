package org.hbrs.academicflow.model.rating;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hbrs.academicflow.model.company.profile.CompanyProfile;
import org.hbrs.academicflow.model.student.user.Student;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingDTO {

  private Instant timestamp;
  private int value;
  private Student student;
  private CompanyProfile companyProfile;

  public Rating buildRatingFromDTO() {
    return Rating.builder()
        .timestamp(this.timestamp)
        .companyProfile(this.companyProfile)
        .student(this.student)
        .value(this.value)
        .build();
  }
}
