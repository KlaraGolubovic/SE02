package org.hbrs.academicflow.model.permission;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hbrs.academicflow.model.user.User;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "permission_group", schema = "public")
public class PermissionGroup {
  @Id
  @Column(
      name = "group_name",
      unique = true,
      nullable = false,
      columnDefinition = "VARCHAR(36) DEFAULT 'GroupName'")
  private String name = "";

  @Column(name = "level", nullable = false)
  private int level = -1;

  @ManyToMany(mappedBy = "groups", fetch = FetchType.EAGER)
  private List<User> users = Lists.newArrayList();

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || this.getClass() != obj.getClass()) {
      return false;
    }
    final PermissionGroup group = (PermissionGroup) obj;
    return Objects.equals(this.name, group.name) && this.level == group.level;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.name, this.level);
  }
}
