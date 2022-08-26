package org.hbrs.academicflow.model.permission;

import java.util.List;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionGroupRepository extends JpaRepository<PermissionGroup, UUID> {

  void deletePermissionGroupByNameEquals(@NotNull String name);

  @Query("select group from PermissionGroup group where group.name=:name")
  @Nullable
  PermissionGroup findPermissionGroupByName(@Param("name") String name);

  @Query("select group.name from PermissionGroup group")
  @NotNull
  List<String> findPermissionGroupNames();
}