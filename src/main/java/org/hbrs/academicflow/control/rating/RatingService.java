package org.hbrs.academicflow.control.rating;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hbrs.academicflow.model.company.profile.CompanyProfile;
import org.hbrs.academicflow.model.rating.Rating;
import org.hbrs.academicflow.model.rating.RatingRepository;
import org.hbrs.academicflow.model.student.user.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
public class RatingService {

  private final RatingRepository repository;

  public Rating doCreateRating(Rating r) {
    Rating existing =
        this.findRatingByStudentAndCompanyProfile(r.getStudent(), r.getCompanyProfile());
    if (existing == null) {
      log.info("Rating created: {}", r);
      return this.repository.save(r);
    } else {
      log.info("Rating already exists: {}", existing);
      return existing;
    }
  }

  private Rating findRatingByStudentAndCompanyProfile(
      Student student, CompanyProfile companyProfile) {
    return this.repository.findByStudentAndCompanyProfile(student, companyProfile);
  }

  public void delete(Rating rating) {
    this.repository.delete(rating);
  }

  public List<Rating> findAll() {
    return this.repository.findAll();
  }
}
