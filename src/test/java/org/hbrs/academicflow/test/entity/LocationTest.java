package org.hbrs.academicflow.test.entity;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hbrs.academicflow.control.location.LocationService;
import org.hbrs.academicflow.model.location.Location;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class LocationTest {

  private static Location storedlLocation;
  @Autowired
  private LocationService locationService;

  @Test
  @Order(1)
  synchronized void testCreateLocation() {
    Location location = Location.manualBuilder().street("Test Location Street").having()
        .city("TestCity").and().also().zipCode("6422DE").country("Deutschland").furthermore()
        .houseNumber("44a").build();
    assertDoesNotThrow(() -> storedlLocation = locationService.save(location));
  }

  @Test
  @Order(2)
  synchronized void testReadLocation() {
    assertDoesNotThrow(() -> {
      Location location = locationService.findById(storedlLocation.getId());
      log.info("Location: {}", location);
    });
  }

  @Test
  @Order(3)
  synchronized void testUpdateLocation() {
    storedlLocation.setStreet("Test Location Street Updated");
    assertDoesNotThrow(() -> storedlLocation = locationService.save(storedlLocation));
  }

  @Test
  @Order(4)
  synchronized void testDeleteLocation() {
    assertDoesNotThrow(() -> locationService.deleteById(storedlLocation.getId()));
  }
}
