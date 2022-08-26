package org.hbrs.academicflow.model.advertisement;

import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hbrs.academicflow.model.company.user.CompanyDTO;
import org.hbrs.academicflow.model.location.LocationDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisementDTO {

  private UUID id;
  private String title;
  private Instant deadline;
  private String description;
  private String jobType;
  private Boolean active;
  private LocationDTO location;
  private Boolean remote;
  private CompanyDTO company;
}
