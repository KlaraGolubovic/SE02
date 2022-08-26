package org.hbrs.academicflow.control.location;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.hbrs.academicflow.model.location.Location;
import org.hbrs.academicflow.model.location.LocationDTO;
import org.hbrs.academicflow.model.location.LocationRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class LocationService {

  private final LocationMapper mapper;
  private final LocationRepository repository;

  public @NotNull Location save(Location location) {
    return this.repository.save(location);
  }

  public @NotNull Location save(LocationDTO location) {
    return this.repository.save(mapper.toEntity(location));
  }

  public void deleteById(UUID id) {
    this.repository.deleteById(id);
  }

  public @NotNull List<Location> findAll() {
    return this.repository.findAll();
  }

  public Location findById(UUID id) {
    return this.repository.findById(id).orElse(null);
  }
}
