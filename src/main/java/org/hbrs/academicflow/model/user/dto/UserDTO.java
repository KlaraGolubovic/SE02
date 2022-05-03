package org.hbrs.academicflow.model.user.dto;

import java.util.List;
import org.hbrs.academicflow.model.permission.dto.PermissionGroupDTO;

public interface UserDTO {
  int getId();

  String getUsername();

  String getEmail();

  List<PermissionGroupDTO> getGroups();
}
