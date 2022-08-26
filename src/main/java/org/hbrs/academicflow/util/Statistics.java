package org.hbrs.academicflow.util;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hbrs.academicflow.control.advertisement.AdvertisementService;
import org.hbrs.academicflow.control.apply.ApplyService;
import org.hbrs.academicflow.control.company.user.CompanyService;
import org.hbrs.academicflow.control.student.StudentService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public final class Statistics {

  private final AdvertisementService advertisementService;
  private final CompanyService companyService;
  private final StudentService studentService;
  private final ApplyService applyService;

  public int getAmountOfJobAds() {
    return advertisementService.findAll().size();
  }

  public int getAmountOfCompanies() {
    return companyService.findAll().size();
  }

  public int getAmountOfStudents() {
    return studentService.findAll().size();
  }

  public int getAmountOfApplies() {
    return applyService.findAll().size();
  }
}
