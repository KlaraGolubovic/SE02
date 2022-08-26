package org.hbrs.academicflow.model.user;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hbrs.academicflow.model.permission.PermissionGroupDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

  private UUID id;
  private String username;
  private String email;
  private List<PermissionGroupDTO> groups;
}
