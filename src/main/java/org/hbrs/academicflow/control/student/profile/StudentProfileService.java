package org.hbrs.academicflow.control.student.profile;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hbrs.academicflow.model.student.profile.StudentProfile;
import org.hbrs.academicflow.model.student.profile.StudentProfileRepository;
import org.hbrs.academicflow.model.user.User;
import org.hbrs.academicflow.model.user.UserDTO;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class StudentProfileService {

  private final StudentProfileRepository repository;

  public @Nullable StudentProfile findStudentProfileByUser(@NotNull UserDTO user) {
    return this.repository.findStudentProfileByUser(user.getId());
  }

  public @Nullable StudentProfile findStudentProfileByUser(@NotNull User user) {
    return this.repository.findStudentProfileByUser(user.getId());
  }

  public @Nullable StudentProfile updateStudentProfile(@NotNull StudentProfile profile) {
    return this.repository.save(profile);
  }

  public @NotNull List<StudentProfile> findAll() {
    return this.repository.findAll();
  }

  public void deleteStudentProfile(@NotNull StudentProfile profile) {
    this.repository.delete(profile);
  }
}
