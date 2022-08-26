package org.hbrs.academicflow.control.student;

import org.hbrs.academicflow.model.student.user.Student;
import org.hbrs.academicflow.model.student.user.StudentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StudentMapper {

  @Mapping(target = "id", source = "id")
  @Mapping(target = "dateOfBirth", source = "dateOfBirth")
  @Mapping(target = "firstName", source = "firstName")
  @Mapping(target = "lastName", source = "lastName")
  @Mapping(target = "phone", source = "phone")
  @Mapping(target = "user", source = "user")
  StudentDTO toDto(Student user);
}
