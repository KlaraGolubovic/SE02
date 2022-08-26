package org.hbrs.academicflow.test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import lombok.RequiredArgsConstructor;
import org.hbrs.academicflow.control.advertisement.SearchProxy;
import org.hbrs.academicflow.view.routes.advertisement.AdvertisementSearch;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class AdSearchTest {

  @Autowired
  private SearchProxy adService;

  @Test
  void testContainer() {
    AdvertisementSearch va = new AdvertisementSearch(adService);

    assertEquals(0, va.getChildren().count());
    assertDoesNotThrow(va::init);
    assertEquals(4, va.getChildren().count());
    assertTrue(va.toString()
        .contains("org.hbrs.academicflow.view.routes.advertisement.AdvertisementSearch"));
  }
}
