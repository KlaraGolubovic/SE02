package org.hbrs.academicflow.model.user.dto;

import org.hbrs.academicflow.model.permission.dto.PermissionGroupDTO;

import java.util.List;

public interface UserDTO {
    int getId();

    String getFirstName();

    String getLastName();

    List<PermissionGroupDTO> getGroups();
}
