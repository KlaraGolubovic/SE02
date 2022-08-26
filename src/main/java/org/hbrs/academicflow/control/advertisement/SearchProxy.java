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
public class SearchProxy implements Search {

  private final SearchControl searchControl;

  /*
   * Hier kann ein Caching umgesetzt werden.
   * Dazu muss die Klasse Suchanfrage Comparable sein, um
   * sicherzustellen, dass folgende "Equivalente" Suchanfragen
   * als solche identifizierbar sind.
   * Dann kann in der methode suche() ein HashMap verwendet werden,
   * welche aus Suchanfragen die Antworten welche Produziert wurden
   * zurückgibt. Hier müsste ggf auch noch ein Timer implementiert werden,
   * damit der Cache nicht tagealte Antworten vorhält
   */

  @Override
  public List<Advertisement> suche(Suchanfrage suchanfrage) {
    log.info(
        "Searching for advertisements matching the given criteria by forwarding the request to search control");
    return this.searchControl.suche(suchanfrage);
  }
}
