package org.hbrs.academicflow.test.entity;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashSet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hbrs.academicflow.control.company.profile.CompanyProfileService;
import org.hbrs.academicflow.control.company.user.CompanyService;
import org.hbrs.academicflow.control.exception.DatabaseUserException;
import org.hbrs.academicflow.control.location.LocationService;
import org.hbrs.academicflow.control.permission.PermissionGroupService;
import org.hbrs.academicflow.control.user.UserService;
import org.hbrs.academicflow.model.company.profile.CompanyProfile;
import org.hbrs.academicflow.model.company.user.Company;
import org.hbrs.academicflow.model.location.Location;
import org.hbrs.academicflow.model.permission.PermissionGroup;
import org.hbrs.academicflow.model.user.User;
import org.hbrs.academicflow.test.common.DefaultValues;
import org.hbrs.academicflow.util.Encryption;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Slf4j
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class CompanyTest {

  private static User storedUser;
  private static CompanyProfile storedCompanyProfile;
  @Autowired
  private CompanyService companyService;
  @Autowired
  private UserService userService;
  @Autowired
  private PermissionGroupService permissionGroupService;
  @Autowired
  private CompanyProfileService companyProfileService;
  @Autowired
  private LocationService locationService;

  @Test
  @Order(1)
  synchronized void create() {
    PermissionGroup lPermissionGroup = new PermissionGroup("OrganisationCompanyTest", 50,
        new HashSet<>());
    try {
      this.permissionGroupService.save(lPermissionGroup);
    } catch (Exception e) {
      log.info("PermissionGroupService could not save PermissionGroup: {}", e.getMessage());
    }
    HashSet<PermissionGroup> ug = new HashSet<>();
    User u = User.builder().username("Temp2CompanyTest")
        .password(Encryption.sha256("Temp2CompanyTestgaPaul"))
        .email("Temp2CompanyTest@Temp2CompanyTest.de").groups(ug).build();
    try {
      storedUser = this.userService.createUser(u);
    } catch (DatabaseUserException e1) {
      storedUser = this.userService.findUserByEmail("Temp2CompanyTest@Temp2CompanyTest.de");
      log.error("UserService could not create User");
    }
    Company compLocal = Company.builder().name("TempTest").phone("1111111111").user(storedUser)
        .build();
    Company storedCompany = assertDoesNotThrow(
        () -> this.companyService.doCreateCompany(compLocal));
    Location loc = DefaultValues.DEFAULT_LOCATION;
    Location storedlLocation = this.locationService.save(loc);
    CompanyProfile compProfilLocal = CompanyProfile.builder().company(storedCompany)
        .description("Hi i am a Test!").image(-1).location(storedlLocation).build();
    storedCompanyProfile = assertDoesNotThrow(
        () -> this.companyProfileService.updateCompanyProfile(compProfilLocal));
  }

  @Test
  @Order(2)
  synchronized void read() {
    assertNotNull(this.companyService.findCompanyByFullUser(storedUser));
    assertNotNull(this.companyProfileService.findCompanyProfileByUser(storedUser));
  }

  @Test
  @Order(3)
  synchronized void update() {
    Company comp = this.companyService.findCompanyByFullUser(storedUser);
    String changes = "TempTest2";
    comp.setName(changes);
    this.companyService.updateCompany(comp);
    assertEquals(changes, this.companyService.findCompanyByFullUser(storedUser).getName());
    CompanyProfile compP = this.companyProfileService.findCompanyProfileByUser(storedUser);
    compP.setDescription(changes);
    storedCompanyProfile = this.companyProfileService.updateCompanyProfile(compP);
    assertEquals(changes, storedCompanyProfile.getDescription());
  }

  @Test
  @Order(4)
  synchronized void delete() {
    assertDoesNotThrow(() -> this.companyProfileService.deleteCompanyProfile(storedCompanyProfile));
    // This deletes the company as well because orphanremoval is set to true in the companyprofile
  }

  @Test
  @Transactional
  @Order(5)
  synchronized void clean() {
    try {
      this.permissionGroupService.deletePermissionGroupByName("OrganisationCompanyTest");
      this.userService.deleteById(storedUser.getId());
    } catch (Exception e) {
      log.error("Could not clean up: " + e.getMessage());
    }
  }
}
