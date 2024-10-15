package org.hbrs.academicflow.test.entity;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.Instant;
import java.util.HashSet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hbrs.academicflow.control.advertisement.AdvertisementService;
import org.hbrs.academicflow.control.apply.ApplyService;
import org.hbrs.academicflow.control.company.user.CompanyService;
import org.hbrs.academicflow.control.exception.DatabaseUserException;
import org.hbrs.academicflow.control.location.LocationService;
import org.hbrs.academicflow.control.permission.PermissionGroupService;
import org.hbrs.academicflow.control.student.StudentService;
import org.hbrs.academicflow.control.user.UserService;
import org.hbrs.academicflow.model.advertisement.Advertisement;
import org.hbrs.academicflow.model.apply.Apply;
import org.hbrs.academicflow.model.company.user.Company;
import org.hbrs.academicflow.model.location.Location;
import org.hbrs.academicflow.model.permission.PermissionGroup;
import org.hbrs.academicflow.model.student.user.Student;
import org.hbrs.academicflow.model.user.User;
import org.hbrs.academicflow.test.common.DefaultValues;
import org.hbrs.academicflow.util.Encryption;
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
class ApplyTest {

  public static final String TEMP_TEST_STUDENTEN = "TempTestStudenten";
  private static final String ORGAAPPTEST = "OrganisationAppTest";
  private static final String TEMPTEST = "TempTest";
  private static Location storedLocation;
  private static Apply storedApply;
  private static User storedCompanyUser;
  private static User storedStudentUser;
  private static Student storedStudent;
  private static Company storedCompany;
  private static Advertisement storedJobAd;
  private static PermissionGroup storedPermissionGroup;
  @Autowired private PermissionGroupService permissionGroupService;
  @Autowired private LocationService locationService;
  @Autowired private UserService userService;
  @Autowired private CompanyService companyService;
  @Autowired private StudentService studentService;
  @Autowired private AdvertisementService advertisementService;
  @Autowired private ApplyService applyService;

  @Test
  @Order(1)
  synchronized void create() {
    try {
      this.permissionGroupService.deletePermissionGroupByName(ORGAAPPTEST);
    } catch (Exception e) {
      // nothing wrong
    }
    try {
      PermissionGroup pLocal = new PermissionGroup(ORGAAPPTEST, 50, new HashSet<>());
      storedPermissionGroup = this.permissionGroupService.save(pLocal);
    } catch (Exception e) {
      log.error("PermissionGroupService could not save PermissionGroup");
    }
    Location localLoc =
        Location.builder()
            .street("Albert-Nestler-Straße")
            .houseNumber("19")
            .zipCode("76131")
            .city("Karlsruhe")
            .country("Deutschland")
            .build();
    storedLocation = this.locationService.save(localLoc);
    HashSet<PermissionGroup> ug = new HashSet<>();
    ug.add(storedPermissionGroup);
    User u =
        User.builder()
            .username("TempTestCompany")
            .password(Encryption.sha256("OrTempTestCompanygaPaul"))
            .email("TempTestCompany@TempTestCompany.de")
            .groups(ug)
            .build();
    try {
      storedCompanyUser = this.userService.createUser(u);
    } catch (DatabaseUserException e1) {
      storedCompanyUser = this.userService.findUserByEmail("TempTestCompany@TempTestCompany.de");
      log.error("UserService could not create User");
    }
    Company compLocal =
        Company.builder().name(TEMPTEST).phone("1111111111").user(storedCompanyUser).build();
    storedCompany = this.companyService.doCreateCompany(compLocal);
    Advertisement localAd =
        Advertisement.builder()
            .active(true)
            .company(storedCompany)
            .deadline(Instant.now())
            .description(
                "Arch Linux is a general-purpose distribution. Upon installation, only a"
                    + " command-line environment is provided; rather than tearing out unneeded and"
                    + " unwanted packages, the user is offered the ability to build a custom system"
                    + " by choosing among thousands of high-quality packages provided in the"
                    + " official repositories for the x86-64 architecture. ")
            .jobType(TEMPTEST)
            .remote(true)
            .location(storedLocation)
            .title(TEMPTEST)
            .build();
    storedJobAd = this.advertisementService.doCreateAdvertisement(localAd);
    User localStudent =
        User.builder()
            .username(TEMP_TEST_STUDENTEN)
            .password(Encryption.sha256(TEMP_TEST_STUDENTEN))
            .email("TempTestStuenten@TempTestStuenten.de")
            .build();
    try {
      storedStudentUser = this.userService.createUser(localStudent);
      storedStudentUser.setGroups(ug);
    } catch (DatabaseUserException e) {
      log.error("UserService could not create User");
      storedStudentUser = this.userService.findUserByEmail("TempTestStuenten@TempTestStuenten.de");
    }
    Student studentX =
        Student.builder()
            .firstName(TEMP_TEST_STUDENTEN)
            .lastName(TEMP_TEST_STUDENTEN)
            .dateOfBirth(Instant.now())
            .phone("111111111")
            .user(storedStudentUser)
            .build();
    storedStudent = this.studentService.doCreateStudent(studentX);
    if (storedStudent == null) {
      log.error("StudentService could not create Student");
      storedStudent = this.studentService.findStudentByUserID(storedStudentUser.getId());
    }
    Apply applyLocal =
        Apply.builder()
            .advertisement(storedJobAd)
            .applied(Instant.now())
            .note(
                "Whereas many GNU/Linux distributions attempt to be more user-friendly, Arch Linux"
                    + " has always been, and shall always remain user-centric. the proficient"
                    + " GNU/Linux user, or anyone with a do-it-yourself attitude who is willing to"
                    + " read t")
            .student(storedStudent)
            .build();
    storedApply = assertDoesNotThrow(() -> this.applyService.doSaveApplication(applyLocal));
    assertNotNull(storedApply);
  }

  @Test
  @Order(2)
  synchronized void read() {
    assertNotNull(
        this.applyService.findApplicationByStudent(
            DefaultValues.DEFAULT_APPLY.getStudent().getId()));
  }

  @Test
  @Order(3)
  synchronized void update() {
    String change =
        "A few months ago I was a Slackware Junkie. I loved it, and laughed atlike it. APT simply"
            + " wasn’t as good as I would have hoped, and I found the community discouraging. Why"
            + " not Gentoo? I dont want to compile everything, that was my problem.";
    Apply apply = this.applyService.findByApplicationID(storedApply.getId());
    assertNotNull(apply);
    apply.setNote(change);
    this.applyService.doSaveApplication(apply);
    assertEquals(change, this.applyService.findByApplicationID(storedApply.getId()).getNote());
  }

  @Test
  @Order(4)
  synchronized void delete() {
    Apply apply =
        this.applyService.findByStudentAndAdvertisement(storedStudent.getId(), storedJobAd.getId());
    assertDoesNotThrow(() -> this.applyService.delete(apply));
  }

  @Test
  @Order(5)
  synchronized void clean() {
    try {
      this.studentService.deleteStudent(storedStudent);
    } catch (Exception e) {
      // OK: Nothing to delete
    }

    try {
      this.advertisementService.deleteAdvertisementById(storedJobAd.getId());
    } catch (Exception e) {
      // OK: Nothing to delete
    }

    try {
      this.companyService.deleteCompany(storedCompany);
    } catch (Exception e) {
      // OK: Nothing to delete
    }
    try {
      this.userService.deleteById(storedCompanyUser.getId());
      this.userService.deleteById(storedStudentUser.getId());
    } catch (Exception e) {
      // OK: Nothing to delete
    }
    try {
      this.locationService.deleteById(storedLocation.getId());
    } catch (Exception e) {
      // OK: Nothing to delete
    }
    try {
      this.permissionGroupService.deleteById(storedPermissionGroup.getId());
    } catch (Exception e) {
      // OK: Nothing to delete
    }
    assertNull(this.permissionGroupService.findPermissionGroupByName(ORGAAPPTEST));
  }
}
