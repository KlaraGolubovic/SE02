package org.hbrs.academicflow.test;

import static org.junit.jupiter.api.Assertions.*;

import org.hbrs.academicflow.model.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DatabaseConnectionTest {

  @Autowired private UserService userService;

  @Test
  public void postgresAvailableTest() {
    assertDoesNotThrow(() -> this.userService.findAllUsers(), "Error");
  }
}
