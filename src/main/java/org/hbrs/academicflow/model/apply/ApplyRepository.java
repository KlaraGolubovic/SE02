package org.hbrs.academicflow.model.apply;

import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplyRepository extends JpaRepository<Apply, UUID> {

  @Query(
      "select apply from Apply apply where apply.student.id=:studID and"
          + " apply.advertisement.id=:adID")
  @Nullable
  Apply findByStudentAndAdvertisement(@Param("studID") UUID studID, @Param("adID") UUID adID);

  @Query("select apply from Apply apply where apply.student.user.id=:studID")
  List<Apply> findApplicationsByStudent(@Param("studID") UUID studID);

  @Query("select apply from Apply apply where apply.advertisement.company.id=:id")
  List<Apply> findApplicationsByCompany(@Param("id") UUID id);

  @Query(
      "select apply from Apply apply where apply.advertisement.company.user.id=:id or"
          + " apply.student.user.id=:id")
  List<Apply> findApplicationsByUserID(UUID id);

  @Query("select apply from Apply apply where apply.id =:applyID")
  Apply findByApplyID(@Param("applyID") UUID uuid);
}
