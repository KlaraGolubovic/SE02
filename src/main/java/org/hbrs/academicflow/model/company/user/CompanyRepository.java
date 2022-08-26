package org.hbrs.academicflow.model.company.user;

import java.util.UUID;
import javax.annotation.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CompanyRepository extends JpaRepository<Company, UUID> {

  @Query("select company from Company company where company.user.id=:userId")
  @Nullable
  Company findCompanyByUser(@Param("userId") UUID userId);
}
