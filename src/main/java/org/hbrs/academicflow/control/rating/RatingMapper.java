package org.hbrs.academicflow.control.rating;

import org.hbrs.academicflow.model.rating.Rating;
import org.hbrs.academicflow.model.rating.RatingDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RatingMapper {

  @Mapping(target = "timestamp", source = "timestamp")
  @Mapping(target = "value", source = "value")
  @Mapping(target = "student", source = "student")
  @Mapping(target = "companyProfile", source = "companyProfile")
  RatingDTO toDto(Rating rating);
}
