package org.hbrs.academicflow.control.permission;

import org.hbrs.academicflow.model.permission.PermissionGroup;
import org.hbrs.academicflow.model.permission.PermissionGroupDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PermissionGroupMapper {

  PermissionGroup toEntity(PermissionGroupDTO dto);

  @Mapping(source = "id", target = "id")
  @Mapping(source = "name", target = "name")
  @Mapping(source = "level", target = "level")
  PermissionGroupDTO toDto(PermissionGroup entity);
}
