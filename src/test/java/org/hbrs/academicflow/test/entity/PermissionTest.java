package org.hbrs.academicflow.test.entity;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.HashSet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hbrs.academicflow.control.permission.PermissionGroupService;
import org.hbrs.academicflow.model.permission.PermissionGroup;
import org.hbrs.academicflow.test.common.DefaultValues;
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
class PermissionTest {

  public static final String ORGANISATION_TEST = "OrganisationTEST";
  public static final String ORGANISATION_TEST_2 = "OrganisationTEST2";
  private static final String TEMPORARY_GROUP_NAME = "TemporaryTestingGroupName";
  @Autowired private PermissionGroupService permissionGroupService;

  @Test
  @Order(1)
  synchronized void create() {
    try {
      PermissionGroup pTest = permissionGroupService.findPermissionGroupByName(ORGANISATION_TEST);
      permissionGroupService.deleteById(pTest.getId());
    } catch (Exception e) {
      log.info("PermissionGroupService could not delete PermissionGroup: " + e.getMessage());
    }
    assertDoesNotThrow(() -> permissionGroupService.save(DefaultValues.DEFAULT_PERMISSION_GROUP));
  }

  @Test
  @Order(2)
  synchronized void find() {
    assertDoesNotThrow(() -> permissionGroupService.findPermissionGroupByName(ORGANISATION_TEST));
  }

  @Test
  @Order(3)
  synchronized void update() {
    try {
      PermissionGroup pTest = permissionGroupService.findPermissionGroupByName(ORGANISATION_TEST_2);
      permissionGroupService.deleteById(pTest.getId());
    } catch (Exception e) {
      log.info("PermissionGroupService could not delete PermissionGroup: " + e.getMessage());
    }
    PermissionGroup pTest = permissionGroupService.findPermissionGroupByName(ORGANISATION_TEST);
    pTest.setName(ORGANISATION_TEST_2);
    assertDoesNotThrow(() -> permissionGroupService.save(pTest));
  }

  @Test
  @Order(4)
  synchronized void findUpdated() {
    assertEquals(
        ORGANISATION_TEST_2,
        permissionGroupService.findPermissionGroupByName(ORGANISATION_TEST_2).getName());
  }

  @Test
  @Transactional
  @Order(5)
  synchronized void delete() {
    assertDoesNotThrow(
        () -> permissionGroupService.deletePermissionGroupByName(ORGANISATION_TEST_2));
    assertNull(permissionGroupService.findPermissionGroupByName(ORGANISATION_TEST_2));
  }

  @Test
  @Transactional
  @Order(6)
  synchronized void testCreation() {
    final PermissionGroup before = new PermissionGroup(TEMPORARY_GROUP_NAME, 80, new HashSet<>());
    final PermissionGroup after = this.permissionGroupService.save(before);
    assertNotNull(after);
    assertEquals(before, after);
  }

  @Test
  @Transactional
  @Order(7)
  synchronized void testDeletion() {
    this.permissionGroupService.deletePermissionGroupByName(TEMPORARY_GROUP_NAME);
    final PermissionGroup group =
        this.permissionGroupService.findPermissionGroupByName(TEMPORARY_GROUP_NAME);
    assertNull(group);
  }
}
