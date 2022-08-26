package org.hbrs.academicflow.model.apply;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hbrs.academicflow.model.advertisement.Advertisement;
import org.hbrs.academicflow.model.student.user.Student;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplyDTO {

  private String note;
  private Instant applied;
  private Advertisement advertisement;
  private Student student;

  public Apply buildApplyfromDTO() {
    return Apply.builder()
        .advertisement(this.advertisement)
        .student(this.student)
        .note(this.note)
        .applied(this.applied)
        .build();
  }
}
