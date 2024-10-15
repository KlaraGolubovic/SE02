package org.hbrs.academicflow.test.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.HashSet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hbrs.academicflow.control.UserValidation;
import org.hbrs.academicflow.control.exception.DatabaseUserException;
import org.hbrs.academicflow.control.permission.PermissionGroupService;
import org.hbrs.academicflow.control.user.UserService;
import org.hbrs.academicflow.model.permission.PermissionGroup;
import org.hbrs.academicflow.model.user.User;
import org.hbrs.academicflow.test.common.DefaultValues;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class UserTest {

  private final UserService userService;
  private final PermissionGroupService permissionGroupService;

  @Test
  @Order(1)
  synchronized void testEmailValidation() {
    assertEquals(false, UserValidation.isValidMailAddress("A"));
    assertEquals(true, UserValidation.isNotValidMailAddress("B123"));
  }

  @Test
  @Order(2)
  @Transactional
  synchronized void testCompanyService() {
    // DELETE:
    userService.deleteByUsername(DefaultValues.DEFAULT_USER.getUsername());
    PermissionGroup group = new PermissionGroup("OrganisationTEST", 50, new HashSet<>());
    try {
      group = permissionGroupService.save(group);
    } catch (DataIntegrityViolationException e) {
      log.info("PermissionGroup already exists: {}", e.getMessage());
    }
    User storedUser = null;
    try {
      HashSet<PermissionGroup> ug = new HashSet<>();
      ug.add(group);
      DefaultValues.DEFAULT_USER.setGroups(ug);
      storedUser = userService.createUser(DefaultValues.DEFAULT_USER);
    } catch (DatabaseUserException e) {
      log.error("could not create user: {}", e.getMessage());
    }
    assertNotNull(storedUser);
    if (storedUser == null) {
      fail("Failed to create user");
    } else {
      userService.deleteById(storedUser.getId());
    }
    try {
      permissionGroupService.deletePermissionGroupByName("OrganisationTEST");
    } catch (Exception e) {
      log.error("could not delete permission group:" + e.getMessage());
    }
  }
}
