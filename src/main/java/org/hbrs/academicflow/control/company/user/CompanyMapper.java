package org.hbrs.academicflow.control.company.user;

import org.hbrs.academicflow.model.company.user.Company;
import org.hbrs.academicflow.model.company.user.CompanyDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

  @Mapping(target = "id", source = "id")
  @Mapping(target = "name", source = "name")
  @Mapping(target = "phone", source = "phone")
  @Mapping(target = "user", source = "user")
  CompanyDTO toDTO(Company company);
}
