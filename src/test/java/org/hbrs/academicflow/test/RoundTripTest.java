package org.hbrs.academicflow.test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hbrs.academicflow.control.student.StudentService;
import org.hbrs.academicflow.control.user.UserService;
import org.hbrs.academicflow.model.student.user.Student;
import org.hbrs.academicflow.model.student.user.StudentRepository;
import org.hbrs.academicflow.model.user.User;
import org.hbrs.academicflow.util.Encryption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class RoundTripTest {

  private static final String DEFAULT_LAST_NAME = "Michel";
  private static final String DEFAULT_FIRST_NAME = "Torben";
  private static final String DEFAULT_USER_NAME = "VogelBanane";
  private static final String DEFAULT_MAIL = "paul200@myserver.de";
  private final UserService userService;
  private final StudentService studentService;
  private final StudentRepository studentRepository;

  @BeforeEach
  void cleanUp() {
    int deletedUsers = 0;
    int deletedStudents = 0;
    User user = this.userService.findUserByEmail(DEFAULT_MAIL);
    if (user != null) {
      final Student student = this.studentService.findStudentByUserID(user.getId());
      if (student != null) {
        this.studentRepository.deleteById(student.getId());
        deletedStudents++;
      }
      deletedUsers++;
      this.userService.deleteById(user.getId());
    }
    log.info("Total deletions User={} and Student={}", deletedUsers, deletedStudents);
  }

  @Test
  void createReadAndDeleteStudent() {
    // Start: user creation
    final User rawUser = assertDoesNotThrow(
        () -> User.builder().username(DEFAULT_USER_NAME).email(DEFAULT_MAIL)
            .password(Encryption.sha256("VogelSpass03")).build());
    User userTry;
    try {
      userTry = this.userService.createUser(rawUser);
    } catch (Exception e) {
      userTry = null;
    }
    final User user = userTry;
    if (user == null) {
      fail("User was NULL!");
      return;
    }
    // End: user creation
    // Start: student creation
    final Student rawStudent = Student.builder().firstName(DEFAULT_FIRST_NAME)
        .lastName(DEFAULT_LAST_NAME).dateOfBirth(Instant.now()).user(user).build();
    final Student student = this.studentRepository.save(rawStudent);
    assertNotNull(student);
    // End: student creation
    // Start: assertions
    assertNotNull(user.getEmail());
    assertEquals(DEFAULT_MAIL, user.getEmail());
    assertEquals(DEFAULT_USER_NAME, user.getUsername());
    assertEquals(DEFAULT_FIRST_NAME, student.getFirstName());
    assertEquals(DEFAULT_LAST_NAME, student.getLastName());
    // End: assertions
    // Start: delete previously created data
    final Student toDelete = this.studentService.findStudentByUserID(user.getId());
    if (toDelete != null) {
      this.studentRepository.deleteById(toDelete.getId());
    }
    this.userService.deleteByUsername(user.getUsername());
    // End: delete previously created data
    assertNull(this.userService.findByUsername(user.getUsername()));
    assertNull(this.studentService.findStudentByUserID(user.getId()));
  }
}
