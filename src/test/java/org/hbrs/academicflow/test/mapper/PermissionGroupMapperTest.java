package org.hbrs.academicflow.test.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.hbrs.academicflow.control.permission.PermissionGroupMapper;
import org.hbrs.academicflow.model.permission.PermissionGroup;
import org.hbrs.academicflow.model.permission.PermissionGroupDTO;
import org.hbrs.academicflow.test.common.DefaultValues;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PermissionGroupMapperTest {

  @Test
  void testMapper() {
    final PermissionGroupMapper mapper = Mappers.getMapper(PermissionGroupMapper.class);
    final PermissionGroup entity = DefaultValues.DEFAULT_PERMISSION_GROUP;
    final PermissionGroupDTO dto = mapper.toDto(entity);
    assertEquals(entity.getName(), dto.getName());
    assertEquals(entity.getLevel(), dto.getLevel());
  }
}
