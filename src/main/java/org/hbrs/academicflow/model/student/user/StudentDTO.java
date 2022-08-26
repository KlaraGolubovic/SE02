package org.hbrs.academicflow.model.student.user;

import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hbrs.academicflow.model.user.UserDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {

  private UUID id;
  private Instant dateOfBirth;
  private String firstName;
  private String lastName;
  private String phone;
  private UserDTO user;
}
