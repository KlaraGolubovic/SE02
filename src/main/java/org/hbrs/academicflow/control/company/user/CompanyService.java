package org.hbrs.academicflow.control.company.user;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hbrs.academicflow.model.company.user.Company;
import org.hbrs.academicflow.model.company.user.CompanyRepository;
import org.hbrs.academicflow.model.user.User;
import org.hbrs.academicflow.model.user.UserDTO;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CompanyService {

  private final CompanyRepository repository;

  public @Nullable Company findCompanyByUser(@NotNull UserDTO user) {
    return this.repository.findCompanyByUser(user.getId());
  }

  public @Nullable Company findCompanyByFullUser(@NotNull User user) {
    return this.repository.findCompanyByUser(user.getId());
  }

  public @Nullable Company updateCompany(@NotNull Company company) {
    return this.repository.save(company);
  }

  public @Nullable Company doCreateCompany(@NotNull Company company) {
    if (this.repository.findCompanyByUser(company.getUser().getId()) != null) {
      throw new IllegalArgumentException("User for company alreaedy exists");
    }
    return this.repository.save(company);
  }

  public @NotNull List<Company> findAll() {
    return this.repository.findAll();
  }

  public void deleteCompany(@NotNull Company company) {
    this.repository.deleteById(company.getId());
  }
}
