package org.hbrs.appname.model.user.dto;

import org.hbrs.appname.model.permission.dto.PermissionGroupDTO;

import java.util.List;

public interface UserDTO {
    int getId();
    String getFirstName();
    String getLastName();
    List<PermissionGroupDTO> getGroups();
}
