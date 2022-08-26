package org.hbrs.academicflow.model.company.profile;

import java.util.UUID;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyProfileRepository extends JpaRepository<CompanyProfile, UUID> {

  @Query("select profile from CompanyProfile profile where profile.company.user.id=:userId")
  @Nullable
  CompanyProfile findCompanyProfileByUser(@Param("userId") UUID userId);

  @Query("select profile from CompanyProfile profile where profile.company.id=:companyId")
  @Nullable
  CompanyProfile findCompanyByCompanyId(@Param("companyId") UUID companyId);
}
