package org.hbrs.academicflow.model.permission.dto;

import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hbrs.academicflow.model.permission.PermissionGroup;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PermissionGroupDTOImpl implements PermissionGroupDTO {
  private String name;

  public PermissionGroupDTOImpl(PermissionGroup group) {
    this.name = group.getName();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || this.getClass() != obj.getClass()) {
      return false;
    }
    final PermissionGroupDTOImpl group = (PermissionGroupDTOImpl) obj;
    return Objects.equals(this.name, group.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.name);
  }
}
