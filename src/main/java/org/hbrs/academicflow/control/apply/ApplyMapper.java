package org.hbrs.academicflow.control.apply;

import org.hbrs.academicflow.model.apply.Apply;
import org.hbrs.academicflow.model.apply.ApplyDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ApplyMapper {

  @Mapping(target = "note", source = "note")
  @Mapping(target = "applied", source = "applied")
  @Mapping(target = "advertisement", source = "advertisement")
  @Mapping(target = "student", source = "student")
  ApplyDTO toDTO(Apply apply);
}
