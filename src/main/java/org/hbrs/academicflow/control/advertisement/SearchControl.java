package org.hbrs.academicflow.control.advertisement;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hbrs.academicflow.model.advertisement.Advertisement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SearchControl implements Search {

  private final AdvertisementService advertisementService;

  @Override
  public List<Advertisement> suche(Suchanfrage suchanfrage) {
    log.info("Searching for advertisements matching the given criteria inside the SearchControl");
    return this.advertisementService.findAdsMatching(suchanfrage);
  }
}
