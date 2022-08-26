package org.hbrs.academicflow.model.permission;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionGroupDTO {

  private UUID id;
  private String name;
  private Integer level;
}
