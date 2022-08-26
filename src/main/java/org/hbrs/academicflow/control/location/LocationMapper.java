package org.hbrs.academicflow.control.location;

import java.util.UUID;
import org.hbrs.academicflow.model.location.Location;
import org.hbrs.academicflow.model.location.LocationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LocationMapper {

  @Mapping(target = "street", source = "street")
  @Mapping(target = "houseNumber", source = "houseNumber")
  @Mapping(target = "city", source = "city")
  @Mapping(target = "zipCode", source = "zipCode")
  @Mapping(target = "country", source = "country")
  LocationDTO toDTO(Location location);

  @Mapping(target = "street", source = "street")
  @Mapping(target = "houseNumber", source = "houseNumber")
  @Mapping(target = "city", source = "city")
  @Mapping(target = "zipCode", source = "zipCode")
  @Mapping(target = "country", source = "country")
  Location toEntity(LocationDTO dto);

  @Mapping(target = "id", source = "locationId")
  @Mapping(target = "street", source = "dto.street")
  @Mapping(target = "houseNumber", source = "dto.houseNumber")
  @Mapping(target = "city", source = "dto.city")
  @Mapping(target = "zipCode", source = "dto.zipCode")
  @Mapping(target = "country", source = "dto.country")
  Location update(LocationDTO dto, UUID locationId, @MappingTarget Location location);
}
