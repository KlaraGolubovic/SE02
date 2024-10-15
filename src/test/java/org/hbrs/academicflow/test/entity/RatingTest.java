package org.hbrs.academicflow.test.entity;

import static com.helger.commons.mock.CommonsAssert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hbrs.academicflow.control.rating.RatingService;
import org.hbrs.academicflow.model.company.profile.CompanyProfile;
import org.hbrs.academicflow.model.rating.Rating;
import org.hbrs.academicflow.model.student.user.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;

@SpringBootTest
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class RatingTest {

  private final RatingService ratingService;

  @Test
  void testRating() {
    Rating rating = new Rating();
    rating.setValue(2);
    rating.setStudent(new Student());
    rating.setTimestamp(Instant.now());
    rating.setCompanyProfile(new CompanyProfile());
    assertEquals(2, rating.getValue());
    log.info("Rating: {}", rating);
  }

  // test if the rating is saved in the database
  @Test
  void crudTest() {
    Rating rating = new Rating();
    rating.setValue(2);
    rating.setStudent(new Student());
    rating.setTimestamp(Instant.now());
    rating.setCompanyProfile(new CompanyProfile());
    assertThrows(
        JpaObjectRetrievalFailureException.class, () -> this.ratingService.doCreateRating(rating));
  }
}
