package org.hbrs.academicflow.model.company.user;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hbrs.academicflow.model.user.UserDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDTO {

  private UUID id;
  private String name;
  private String phone;
  private UserDTO user;
}