package org.hbrs.academicflow.test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.hbrs.academicflow.control.exception.DatabaseUserException;
import org.hbrs.academicflow.control.user.UserService;
import org.hbrs.academicflow.util.Constants;
import org.hbrs.academicflow.util.Constants.Pages;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DatabaseConnectionTest {

  @Autowired private UserService userService;

  @Test
  void postgresAvailableTest() {
    assertDoesNotThrow(this.userService::findAllUsers, "Error");
  }

  @Test
  void defaultsTest() {
    assertTrue(Constants.organizationOnlyRefs().contains(Pages.ORGANIZATION_LIST_ADS));
    assertTrue(Constants.organizationOnlyRefs().contains(Pages.ORGANIZATION_CREATE_AD));
    assertTrue(Constants.organizationOnlyRefs().contains(Pages.ORGANIZATION_PROFILE_VIEW));
    assertTrue(Constants.organizationOnlyRefs().contains(Pages.ORGANIZATION_PROFILE_EDIT_VIEW));
  }

  @Test
  void exceptionTest() {
    DatabaseUserException dbu = new DatabaseUserException();
    assertEquals("An error occured while executing a query", dbu.getReason());
  }
}
