package org.hbrs.academicflow.model.rating;

import java.util.List;
import java.util.UUID;
import org.hbrs.academicflow.model.company.profile.CompanyProfile;
import org.hbrs.academicflow.model.student.user.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Rating, UUID> {

  @Query("select rating from Rating rating where rating.companyProfile.company.id=:compID")
  List<Rating> findRatingsByCompany(@Param("compID") UUID compID);

  @Query("select rating from Rating rating where rating.companyProfile.id=:compProfileID")
  List<Rating> findRatingsByCompanyProfile(@Param("compProfileID") UUID compProfileID);

  Rating findByStudentAndCompanyProfile(Student student, CompanyProfile companyProfile);
}
