package org.hbrs.academicflow.control.advertisement;

import java.util.List;
import org.hbrs.academicflow.model.advertisement.Advertisement;

public interface Search {

  List<Advertisement> suche(Suchanfrage suchanfrage);
}
