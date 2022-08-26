package org.hbrs.academicflow.test.entity;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hbrs.academicflow.model.student.user.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class StudentTest {

  @Test
  void testStudent() {
    Student student = new Student();
    assertDoesNotThrow(() -> {
      student.setFirstName("Test");
      student.setLastName("Test");
      student.setPhone("Test");
      student.setDateOfBirth(Instant.now());

    });
    assertThrows(Exception.class, () -> student.setFirstName(null));
    assertThrows(Exception.class, () -> student.setLastName(null));

  }
}


