package org.hbrs.academicflow.control.advertisement;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hbrs.academicflow.model.advertisement.Advertisement;
import org.hbrs.academicflow.model.advertisement.AdvertisementDTO;
import org.hbrs.academicflow.model.advertisement.AdvertisementRepository;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AdvertisementService {

  private final AdvertisementMapper mapper;
  private final AdvertisementRepository repository;

  /**
   * This function will return all existing advertisements
   *
   * @return List of all existing {@link AdvertisementDTO}s
   */
  public @NotNull List<AdvertisementDTO> findAdvertisements() {
    return this.repository.findAll().stream()
        .filter(Advertisement::getActive)
        .map(this.mapper::toDTO)
        .collect(Collectors.toList());
  }

  public Advertisement doCreateAdvertisement(Advertisement ad) {
    return this.repository.save(ad);
  }

  public List<Advertisement> findAll() {
    return this.repository.findAll();
  }

  /**
   * This function will return all existing advertisements created by a given
   * {@link org.hbrs.academicflow.model.company.user.Company}
   *
   * @param companyId which has created the requested advertisements
   * @return List of all existing {@link AdvertisementDTO}s created by the given companyId
   */
  public @NotNull List<AdvertisementDTO> findAdvertisementsByCompanyId(@NotNull UUID companyId) {
    return this.repository.findAdvertisementsByCompanyId(companyId).stream()
        .map(this.mapper::toDTO)
        .collect(Collectors.toList());
  }

  /**
   * This function will return the advertisement with a specific id
   *
   * @param advertisementId for which an
   *                        {@link org.hbrs.academicflow.model.advertisement.Advertisement} should
   *                        be searched
   * @return Either the found {@link AdvertisementDTO} or null if there is no advertisement with the
   * given id
   */
  public @Nullable AdvertisementDTO findAdvertisementById(@NotNull UUID advertisementId) {
    return this.repository.findById(advertisementId).map(this.mapper::toDTO).orElse(null);
  }

  /**
   * This function will delete an {@link org.hbrs.academicflow.model.advertisement.Advertisement}
   * with a given id
   *
   * @param advertisementId for which an
   *                        {@link org.hbrs.academicflow.model.advertisement.Advertisement} should
   *                        be deleted
   */
  public void deleteAdvertisementById(@NotNull UUID advertisementId) {
    this.repository.deleteById(advertisementId);
  }

  /**
   * This function will delete all {@link org.hbrs.academicflow.model.advertisement.Advertisement}s
   * which are created by the given companyId
   *
   * @param companyId which has created the deletable advertisements
   */
  public void deleteAdvertisementsByCompanyId(@NotNull UUID companyId) {
    this.repository.deleteAdvertisementsByCompanyId(companyId);
  }

  /**
   * This function will update an {@link org.hbrs.academicflow.model.advertisement.Advertisement}
   * and return the updated {@link org.hbrs.academicflow.model.advertisement.Advertisement}
   *
   * @param advertisement
   * @return
   */
  public @Nullable Advertisement updateAdvertisement(@NotNull Advertisement advertisement) {
    return this.repository.save(advertisement);
  }

  /**
   * This function will filter all {@link org.hbrs.academicflow.model.advertisement.Advertisement}s
   * by their remote attribute
   */
  public @NotNull List<AdvertisementDTO> findAdvertisementsByIsRemote(boolean remote) {
    return this.repository.findAdvertisementsByRemote(remote).stream()
        .map(this.mapper::toDTO)
        .collect(Collectors.toList());
  }

  public List<AdvertisementDTO> findAdvertisementsBySearchTerm(String searchTerm) {
    // use the mapper
    return this.repository.findAdvertisementsBySearchTerm(searchTerm).stream()
        .map(this.mapper::toDTO)
        .collect(Collectors.toList());
  }

  /**
   * This function will return the advertisement with a specific id
   *
   * @param advertisementId for which an
   *                        {@link org.hbrs.academicflow.model.advertisement.Advertisement} should
   *                        be searched
   * @return Either the found {@link Advertisement} or null if there is no advertisement with the
   * given id
   */
  public Advertisement findFullAdvertisementById(@NotNull UUID advertisementId) {
    return this.repository.findById(advertisementId).orElse(null);
  }

  /**
   * This function will deactivate the specified advertisement
   *
   * @param advertisementId
   */
  public void deactivateAdvertisementsById(@NotNull UUID advertisementId) {
    this.repository.deactivateAdvertisementsById(advertisementId);
  }

  public List<Advertisement> findAdsMatching(Suchanfrage suchanfrage) {
    List<Advertisement> all = this.repository.findAll();
    // for each existing filter, apply the filter
    for (Predicate<Advertisement> filter : suchanfrage.getFilters()) {
      log.info("Filter: " + filter);
      all = all.stream().filter(filter).collect(Collectors.toList());
    }

    return all;
  }
}
