package org.hbrs.academicflow.model.student.user;

import java.util.Set;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {

  @Query("select student from Student student where student.user.id=:userId")
  Student findStudentByUserId(@Param("userId") UUID userId);

  @Query("select student from Student student where student.firstName=:firstname and student.lastName=:lastname")
  Set<Student> findStudentsByFirstnameAndLastname(String firstname, String lastname);
}
