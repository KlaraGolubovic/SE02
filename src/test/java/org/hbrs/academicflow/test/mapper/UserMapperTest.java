package org.hbrs.academicflow.test.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Collectors;
import org.hbrs.academicflow.control.user.UserMapper;
import org.hbrs.academicflow.model.permission.PermissionGroup;
import org.hbrs.academicflow.model.permission.PermissionGroupDTO;
import org.hbrs.academicflow.model.user.User;
import org.hbrs.academicflow.model.user.UserDTO;
import org.hbrs.academicflow.test.common.DefaultValues;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserMapperTest {

  @Test
  void testMapper() {
    final UserMapper mapper = Mappers.getMapper(UserMapper.class);
    final User entity = DefaultValues.DEFAULT_USER;
    final UserDTO dto = mapper.toDTO(entity);
    assertEquals(entity.getId(), dto.getId());
    assertEquals(entity.getUsername(), dto.getUsername());
    assertEquals(entity.getEmail(), dto.getEmail());
    assertEquals(
        entity.getGroups().stream().map(PermissionGroup::getName).collect(Collectors.toList()),
        dto.getGroups().stream().map(PermissionGroupDTO::getName).collect(Collectors.toList()));
  }
}
