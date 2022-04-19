package org.hbrs.academicflow.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;
import lombok.RequiredArgsConstructor;
import org.hbrs.academicflow.model.user.User;
import org.hbrs.academicflow.model.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class UserTests {
  private static final String DEMO_USER_ID = "IchBinStudent";

  private final UserService service;

  @Test
  void doTestUserCreation() {
    final User user = new User();
    user.setDateOfBirth(LocalDate.now());
    final int mailId = ThreadLocalRandom.current().nextInt(1000);
    user.setEmail("student" + mailId + "@inf.h-brs.de");
    user.setFirstName("Heinz");
    user.setLastName("Schmidt");
    user.setOccupation("STUDENT");
    user.setPassword("StrongPassword");
    user.setPhone("+49 123 456789");
    user.setUserid(DEMO_USER_ID);
    user.setGroups(Collections.emptyList());

    final User result = this.service.doCreateUser(user);

    assertNotNull(result);
    assertEquals(user.getUserid(), result.getUserid());
  }

  @Test
  void doTestUserDeletion() {
    assertDoesNotThrow(() -> this.service.deleteUser(DEMO_USER_ID));
  }

  @Test
  void doTestUserSelection() {
    assertNull(this.service.findUserById(DEMO_USER_ID));
  }
}
