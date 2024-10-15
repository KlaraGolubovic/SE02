package org.hbrs.academicflow.test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import lombok.RequiredArgsConstructor;
import org.hbrs.academicflow.control.advertisement.AdvertisementService;
import org.hbrs.academicflow.control.company.user.CompanyService;
import org.hbrs.academicflow.view.routes.advertisement.AdvertisementManagement;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class AdListTest {

  private static final Object WEBSTRING = "<div></div>";
  @Autowired private AdvertisementService adService;
  @Autowired private CompanyService companyService;

  @Test
  void testContentsForVisibleAd() {
    AdvertisementManagement va = new AdvertisementManagement(adService, companyService);
    assertEquals(0, va.getChildren().count());
  }

  @Test
  void testBorder() {
    AdvertisementManagement va = new AdvertisementManagement(adService, companyService);
    assertDoesNotThrow(va::toString);
    assertEquals(
        true,
        va.toString()
            .contains("org.hbrs.academicflow.view.routes.advertisement.AdvertisementManagement"));
    assertEquals(WEBSTRING, va.getElement().getOuterHTML());
  }
}
