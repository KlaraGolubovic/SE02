package org.hbrs.academicflow.model.permission;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hbrs.academicflow.model.common.BaseEntity;
import org.hbrs.academicflow.model.user.User;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
    name = "permission_group",
    schema = "public",
    indexes = {@Index(name = "idx_group_name", columnList = "group_name", unique = true)})
public class PermissionGroup extends BaseEntity {

  @NotNull
  @Column(
      name = "group_name",
      unique = true,
      nullable = false,
      columnDefinition = "VARCHAR(36) DEFAULT 'GroupName'")
  private String name = "";
  @NotNull
  @Column(name = "level", nullable = false)
  private Integer level = -1;
  @NotNull
  @ManyToMany(mappedBy = "groups", fetch = FetchType.EAGER)
  private Set<User> users = new HashSet<>();

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || this.getClass() != obj.getClass()) {
      return false;
    }
    if (!super.equals(obj)) {
      return false;
    }
    final PermissionGroup permissionGroup = (PermissionGroup) obj;
    if (!this.name.equals(permissionGroup.name)) {
      return false;
    }
    return this.level.equals(permissionGroup.level);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + this.name.hashCode();
    result = 31 * result + this.level.hashCode();
    return result;
  }
}
