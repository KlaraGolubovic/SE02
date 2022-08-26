package org.hbrs.academicflow.test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import lombok.RequiredArgsConstructor;
import org.hbrs.academicflow.control.advertisement.AdvertisementService;
import org.hbrs.academicflow.control.company.profile.CompanyProfileService;
import org.hbrs.academicflow.control.company.user.CompanyService;
import org.hbrs.academicflow.view.routes.advertisement.AdvertisementCreateView;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class CreateAdTest {

  private static final Object EMPTYDIV = "<div></div>";
  @Autowired
  private AdvertisementService adService;
  @Autowired
  private CompanyService companyService;
  @Autowired
  private CompanyProfileService profileService;

  @Test
  void testContentsForVisibleAd() {
    AdvertisementCreateView va =
        new AdvertisementCreateView(adService, companyService, profileService);
    assertEquals(EMPTYDIV, va.getElement().getOuterHTML());
    assertDoesNotThrow(va::toString);
  }
}
