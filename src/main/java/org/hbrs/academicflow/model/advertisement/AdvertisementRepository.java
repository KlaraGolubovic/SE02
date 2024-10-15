package org.hbrs.academicflow.model.advertisement;

import java.util.List;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, UUID> {

  @Modifying
  @Transactional
  @Query("delete from Advertisement advertisement where advertisement.company.id=:companyId")
  void deleteAdvertisementsByCompanyId(@Param("companyId") UUID companyId);

  @Modifying
  @Transactional
  @Query(
      "update Advertisement advertisement set advertisement.active=:false where"
          + " advertisement.id=:advertisementId")
  void deactivateAdvertisementsById(@Param("advertisementId") UUID advertisementId);

  @Query(
      "select advertisement from Advertisement advertisement "
          + "where advertisement.company.id=:companyId "
          + "and advertisement.active=true")
  List<Advertisement> findAdvertisementsByCompanyId(@Param("companyId") UUID companyId);

  @Query(
      "select advertisement from Advertisement advertisement "
          + "where advertisement.remote=:remote "
          + "and advertisement.active=true")
  List<Advertisement> findAdvertisementsByRemote(@Param("remote") boolean remote);

  @Query(
      "select advertisement from Advertisement advertisement "
          + "where lower(advertisement.description) like lower(concat('%', :suche, '%')) "
          + "or lower(advertisement.title) like lower(concat('%', :suche, '%'))"
          + "and advertisement.active=true")
  List<Advertisement> findAdvertisementsBySearchTerm(@Param("suche") String searchTerm);
}
