package org.hbrs.academicflow.test.entity;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.HashSet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hbrs.academicflow.control.advertisement.AdvertisementService;
import org.hbrs.academicflow.control.company.user.CompanyService;
import org.hbrs.academicflow.control.location.LocationService;
import org.hbrs.academicflow.control.permission.PermissionGroupService;
import org.hbrs.academicflow.control.user.UserService;
import org.hbrs.academicflow.model.advertisement.Advertisement;
import org.hbrs.academicflow.model.permission.PermissionGroup;
import org.hbrs.academicflow.test.common.DefaultValues;
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
class AdvertisementTest {

  private static final String ORGATEST = "OrganisationTEST";
  @Autowired private AdvertisementService adService;
  @Autowired private CompanyService companyService;
  @Autowired private UserService userService;
  @Autowired private LocationService locationService;
  @Autowired private PermissionGroupService permissionGroupService;

  // Test des Repositorys
  // Test des Services
  @Test
  @Order(1)
  synchronized void create() {
    try {
      permissionGroupService.save(
          DefaultValues.DEFAULT_ADVERTISEMENT.getCompany().getUser().getGroups().iterator().next());
    } catch (Exception e) {
      log.info("PermissionGroupService could not save PermissionGroup");
    }
    locationService.save(DefaultValues.DEFAULT_ADVERTISEMENT.getLocation());
    HashSet<PermissionGroup> ug = new HashSet<>();
    ug.add(permissionGroupService.findPermissionGroupByName(ORGATEST));
    DefaultValues.DEFAULT_ADVERTISEMENT.getCompany().getUser().setGroups(ug);
    assertDoesNotThrow(
        () -> userService.createUser(DefaultValues.DEFAULT_ADVERTISEMENT.getCompany().getUser()));
    companyService.doCreateCompany(DefaultValues.DEFAULT_ADVERTISEMENT.getCompany());
    assertDoesNotThrow(() -> adService.doCreateAdvertisement(DefaultValues.DEFAULT_ADVERTISEMENT));
  }

  @Test
  @Order(2)
  synchronized void read() {
    assertNotNull(adService.findAdvertisementById(DefaultValues.DEFAULT_ADVERTISEMENT.getId()));
  }

  @Test
  @Order(3)
  synchronized void update() {
    Advertisement up =
        adService.findFullAdvertisementById(DefaultValues.DEFAULT_ADVERTISEMENT.getId());
    up.setTitle("Test");
    adService.updateAdvertisement(up);
    assertEquals(
        "Test",
        adService.findAdvertisementById(DefaultValues.DEFAULT_ADVERTISEMENT.getId()).getTitle());
  }

  @Test
  @Order(4)
  synchronized void delete() {
    assertNotNull(adService.findAdvertisementById(DefaultValues.DEFAULT_ADVERTISEMENT.getId()));
    adService.deleteAdvertisementById(DefaultValues.DEFAULT_ADVERTISEMENT.getId());
    assertNull(adService.findAdvertisementById(DefaultValues.DEFAULT_ADVERTISEMENT.getId()));
  }

  @Test
  @Order(5)
  synchronized void clean() {
    // delete the user and permissiongroup and company
    try {
      companyService.deleteCompany(DefaultValues.DEFAULT_ADVERTISEMENT.getCompany());
    } catch (Exception e) {
      // do nothing
    }
    try {
      userService.deleteById(DefaultValues.DEFAULT_ADVERTISEMENT.getCompany().getUser().getId());
    } catch (Exception e) {
      // do nothing
    }
    try {
      locationService.deleteById(DefaultValues.DEFAULT_ADVERTISEMENT.getLocation().getId());
    } catch (Exception e) {
      // do nothing
    }
    try {
      permissionGroupService.deletePermissionGroupByName(ORGATEST);
      assertNull(permissionGroupService.findPermissionGroupByName(ORGATEST));
    } catch (Exception e) {
      // do nothing
    }
  }
}
