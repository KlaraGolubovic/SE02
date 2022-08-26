package org.hbrs.academicflow.control.advertisement;

import org.hbrs.academicflow.model.advertisement.Advertisement;
import org.hbrs.academicflow.model.advertisement.AdvertisementDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AdvertisementMapper {

  @Mapping(target = "id", source = "id")
  @Mapping(target = "title", source = "title")
  @Mapping(target = "deadline", source = "deadline")
  @Mapping(target = "description", source = "description")
  @Mapping(target = "jobType", source = "jobType")
  @Mapping(target = "location", source = "location")
  @Mapping(target = "remote", source = "remote")
  @Mapping(target = "company", source = "company")
  AdvertisementDTO toDTO(Advertisement advertisement);
}
