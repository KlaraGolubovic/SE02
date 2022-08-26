package org.hbrs.academicflow.model.student.profile;

import java.util.UUID;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentProfileRepository extends JpaRepository<StudentProfile, UUID> {

  @Query("select profile from StudentProfile profile where profile.student.user.id=:userId")
  @Nullable
  StudentProfile findStudentProfileByUser(@Param("userId") UUID userId);
}
