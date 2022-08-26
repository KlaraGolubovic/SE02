package org.hbrs.academicflow.control.permission;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.hbrs.academicflow.model.permission.PermissionGroup;
import org.hbrs.academicflow.model.permission.PermissionGroupRepository;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PermissionGroupService {

  private final PermissionGroupRepository repository;

  public @NotNull List<PermissionGroup> findAllUncached() {
    return this.repository.findAll();
  }

  public @Nullable PermissionGroup findPermissionGroupByName(@NotNull String name) {
    return this.repository.findPermissionGroupByName(name);
  }

  public @NotNull List<String> findPermissionGroupNames() {
    return this.repository.findPermissionGroupNames();
  }

  public @Nullable PermissionGroup save(@NotNull PermissionGroup permissionGroup) {
    return this.repository.save(permissionGroup);
  }

  public void deletePermissionGroupByName(String name) {
    this.repository.deletePermissionGroupByNameEquals(name);
  }

  public void deleteById(@NotNull UUID userIds) {
    this.repository.deleteById(userIds);
  }
}
