package org.hbrs.academicflow.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import lombok.RequiredArgsConstructor;
import org.hbrs.academicflow.model.common.BaseEntity;
import org.hbrs.academicflow.model.common.VersionedEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@SpringBootTest
class VersionedEntityTest {

  @Test
  @Transactional
  void testVersionedEntity() {
    VersionedEntity versionedEntityA = new VersionedEntity() {
    };
    VersionedEntity versionedEntityB = new VersionedEntity() {
    };
    assertEquals(0, versionedEntityA.getVersion());
    assertEquals(0, versionedEntityB.getVersion());
    assertNotEquals(versionedEntityA, versionedEntityB);
    versionedEntityA.setVersion(1);
    assertEquals(1, versionedEntityA.getVersion());
    assertEquals(0, versionedEntityB.getVersion());
    assertNotEquals(versionedEntityA, versionedEntityB);
    versionedEntityB.setVersion(1);
    assertEquals(1, versionedEntityA.getVersion());
    assertEquals(1, versionedEntityB.getVersion());
  }

  @Test
  @Transactional
  void testEquals() {
    // test == comparator
    VersionedEntity versionedEntityA = new VersionedEntityTestClass();
    assertEquals(versionedEntityA, versionedEntityA);
    // test if null
    assertNotEquals(null, versionedEntityA);
    // below is the assertion which actually calls equals on the entity
    assertEquals(false, versionedEntityA.equals(null));
    // test if not a VersionedEntity
    assertNotEquals("hallo", versionedEntityA);
    BaseEntity baseEntity = new BaseEntity() {
    };
    assertNotEquals(baseEntity, versionedEntityA);
    VersionedEntity versionedEntityB = new VersionedEntityTestClass();
    assertNotEquals(versionedEntityA, versionedEntityB);
    versionedEntityB.setId(versionedEntityA.getId());
    assertEquals(versionedEntityA, versionedEntityB);
    // getClass gibt hier einen Unterschied an, wenn anonyme klassen verwendet werden
    versionedEntityB.setVersion(1);
    assertNotEquals(versionedEntityA, versionedEntityB);
  }

  @Test
  @Transactional
  void testHashCode() {
    VersionedEntity versionedEntityA = new VersionedEntityTestClass();
    assertEquals(versionedEntityA.hashCode(), versionedEntityA.hashCode());
    VersionedEntity versionedEntityB = new VersionedEntityTestClass();
    versionedEntityB.setId(versionedEntityA.getId());
    assertEquals(versionedEntityA.hashCode(), versionedEntityB.hashCode());
  }

  @Test
  @Transactional
  void testToString() {
    VersionedEntity versionedEntityA = new VersionedEntityTestClass();
    assertEquals(
        "VersionedEntity(super=BaseEntity(id=" + versionedEntityA.getId() + "), version=0)",
        versionedEntityA.toString());
  }

  // Inner Test class for testing VersionedEntity
  public static class VersionedEntityTestClass extends VersionedEntity {

  }
}
