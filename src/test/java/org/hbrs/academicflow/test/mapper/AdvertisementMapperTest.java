package org.hbrs.academicflow.test.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.hbrs.academicflow.control.advertisement.AdvertisementMapper;
import org.hbrs.academicflow.model.advertisement.Advertisement;
import org.hbrs.academicflow.model.advertisement.AdvertisementDTO;
import org.hbrs.academicflow.test.common.DefaultValues;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AdvertisementMapperTest {

  @Test
  void testMapper() {
    final AdvertisementMapper mapper = Mappers.getMapper(AdvertisementMapper.class);
    final Advertisement entity = DefaultValues.DEFAULT_ADVERTISEMENT;
    final AdvertisementDTO dto = mapper.toDTO(entity);
    assertEquals(entity.getId(), dto.getId());
    assertEquals(entity.getRemote(), dto.getRemote());
    assertEquals(entity.getActive(), dto.getActive());
    assertEquals(entity.getJobType(), dto.getJobType());
    assertEquals(entity.getLocation().getCountry(), dto.getLocation().getCountry());
    assertEquals(entity.getDeadline(), dto.getDeadline());
    assertEquals(entity.getTitle(), dto.getTitle());
    assertEquals(entity.getCompany().getId(), dto.getCompany().getId());
  }
}
