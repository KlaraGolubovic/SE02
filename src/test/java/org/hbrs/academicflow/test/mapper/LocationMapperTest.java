package org.hbrs.academicflow.test.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.hbrs.academicflow.control.location.LocationMapper;
import org.hbrs.academicflow.model.location.Location;
import org.hbrs.academicflow.model.location.LocationDTO;
import org.hbrs.academicflow.test.common.DefaultValues;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LocationMapperTest {

  @Test
  void testMapper() {
    final LocationMapper mapper = Mappers.getMapper(LocationMapper.class);
    final Location entity = DefaultValues.DEFAULT_LOCATION;
    final LocationDTO dto = mapper.toDTO(entity);
    assertEquals(entity.getCountry(), dto.getCountry());
    assertEquals(entity.getCity(), dto.getCity());
    assertEquals(entity.getHouseNumber(), dto.getHouseNumber());
    assertEquals(entity.getStreet(), dto.getStreet());
    assertEquals(entity.getZipCode(), dto.getZipCode());
  }
}
