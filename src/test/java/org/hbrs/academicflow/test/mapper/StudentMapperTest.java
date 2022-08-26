package org.hbrs.academicflow.test.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.hbrs.academicflow.control.student.StudentMapper;
import org.hbrs.academicflow.model.student.user.Student;
import org.hbrs.academicflow.model.student.user.StudentDTO;
import org.hbrs.academicflow.test.common.DefaultValues;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StudentMapperTest {

  @Test
  void testMapper() {
    final StudentMapper mapper = Mappers.getMapper(StudentMapper.class);
    final Student entity = DefaultValues.DEFAULT_STUDENT;
    final StudentDTO dto = mapper.toDto(entity);
    assertEquals(entity.getFirstName(), dto.getFirstName());
    assertEquals(entity.getId(), dto.getId());
    assertEquals(entity.getLastName(), dto.getLastName());
    assertEquals(entity.getDateOfBirth(), dto.getDateOfBirth());
    assertEquals(entity.getUser().getUsername(), dto.getUser().getUsername());
  }
}
