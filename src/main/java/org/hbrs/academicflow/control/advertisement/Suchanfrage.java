package org.hbrs.academicflow.control.advertisement;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hbrs.academicflow.model.advertisement.Advertisement;

@Setter
@Getter
@Slf4j
public class Suchanfrage {

  private List<Predicate<Advertisement>> filterList;
  private Predicate<Advertisement> filterString;
  private Predicate<Advertisement> filterRemote;

  public Suchanfrage() {
    this.filterList = new ArrayList<>();
    this.filterString = null;
  }

  public List<Predicate<Advertisement>> getFilters() {
    if (filterList == null) {
      log.info("Filter list is null");
      return new ArrayList<>();
    }
    List<Predicate<Advertisement>> fullFilterList = new ArrayList<>(this.filterList);
    fullFilterList.add(Advertisement::getActive);
    if (filterString != null) {
      fullFilterList.add(filterString);
    }
    if (filterRemote != null) {
      fullFilterList.add(filterRemote);
    }
    return fullFilterList;
  }

  public void addFilter(Predicate<Advertisement> filter) {
    this.filterList.add(filter);
  }
}
