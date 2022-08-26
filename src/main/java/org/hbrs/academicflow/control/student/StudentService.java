package org.hbrs.academicflow.control.student;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.hbrs.academicflow.model.student.user.Student;
import org.hbrs.academicflow.model.student.user.StudentRepository;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class StudentService {

  private final StudentRepository repository;

  public @Nullable Student doCreateStudent(@NotNull Student student) {
    return this.repository.save(student);
  }

  public @Nullable Student doUpdateStudent(@NotNull Student student) {
    return this.repository.save(student);
  }

  public @Nullable Student findStudentByUserID(@NotNull UUID userId) {
    return this.repository.findStudentByUserId(userId);
  }

  public @NotNull List<Student> findAll() {
    return this.repository.findAll();
  }

  public void deleteStudent(@NotNull Student student) {
    this.repository.deleteById(student.getId());
  }

  // find Students by firstname and lastname
  public @NotNull Set<Student> findStudentsByFirstnameAndLastname(@NotNull String firstname,
      @NotNull String lastname) {
    return this.repository.findStudentsByFirstnameAndLastname(firstname, lastname);
  }
}
