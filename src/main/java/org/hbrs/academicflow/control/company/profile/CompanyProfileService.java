package org.hbrs.academicflow.control.company.profile;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.hbrs.academicflow.model.company.profile.CompanyProfile;
import org.hbrs.academicflow.model.company.profile.CompanyProfileRepository;
import org.hbrs.academicflow.model.user.User;
import org.hbrs.academicflow.model.user.UserDTO;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CompanyProfileService {

  private final CompanyProfileRepository repository;

  public @Nullable CompanyProfile findCompanyProfileByUser(@NotNull UserDTO user) {
    return this.repository.findCompanyProfileByUser(user.getId());
  }

  public @Nullable CompanyProfile findCompanyProfileByUser(@NotNull User user) {
    return this.repository.findCompanyProfileByUser(user.getId());
  }

  public @Nullable CompanyProfile updateCompanyProfile(@NotNull CompanyProfile profile) {
    return this.repository.save(profile);
  }

  public @NotNull List<CompanyProfile> findAll() {
    return this.repository.findAll();
  }

  public @Nullable CompanyProfile findCompanyByCompanyId(UUID companyId) {
    return this.repository.findCompanyByCompanyId(companyId);
  }

  public void deleteCompanyProfile(@NotNull CompanyProfile companyProfile) {
    this.repository.deleteById(companyProfile.getId());
  }
}
