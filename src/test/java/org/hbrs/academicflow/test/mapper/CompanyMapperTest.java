package org.hbrs.academicflow.test.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.hbrs.academicflow.control.company.user.CompanyMapper;
import org.hbrs.academicflow.model.company.user.Company;
import org.hbrs.academicflow.model.company.user.CompanyDTO;
import org.hbrs.academicflow.test.common.DefaultValues;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CompanyMapperTest {

  @Test
  void testMapper() {
    final CompanyMapper mapper = Mappers.getMapper(CompanyMapper.class);
    final Company entity = DefaultValues.DEFAULT_COMPANY;
    final CompanyDTO dto = mapper.toDTO(entity);
    assertEquals(entity.getId(), dto.getId());
    assertEquals(entity.getName(), dto.getName());
    assertEquals(entity.getPhone(), dto.getPhone());
    assertEquals(entity.getUser().getUsername(), dto.getUser().getUsername());
    assertEquals(entity.getUser().getId(), dto.getUser().getId());
  }
}
