package org.hbrs.academicflow.control.user;

import org.hbrs.academicflow.model.user.User;
import org.hbrs.academicflow.model.user.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper {

  @Mappings(
      value = {
        @Mapping(target = "id", source = "id"),
        @Mapping(target = "username", source = "username"),
        @Mapping(target = "email", source = "email"),
        @Mapping(target = "groups", source = "groups")
      })
  UserDTO toDTO(User user);
}
