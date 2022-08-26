package org.hbrs.academicflow.model.user;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hbrs.academicflow.model.common.BaseEntity;
import org.hbrs.academicflow.model.permission.PermissionGroup;
import org.jetbrains.annotations.NotNull;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
    name = "user",
    schema = "public",
    indexes = {
        @Index(name = "idx_username", columnList = "username"),
        @Index(name = "idx_email", columnList = "email"),
    },
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_username", columnNames = "username"),
        @UniqueConstraint(name = "uk_email", columnNames = "email"),
    })
// uniqueconstraint makes sure a username is unique
public class User extends BaseEntity {

  @NotNull
  @Builder.Default
  @Column(name = "email", nullable = false)
  private String email = "";
  @NotNull
  @Builder.Default
  @Column(name = "password", nullable = false)
  private String password = "";
  @NotNull
  @Builder.Default
  @Column(name = "username", nullable = false)
  private String username = "";
  @NotNull
  @Builder.Default
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "user_group",
      schema = "public",
      joinColumns =
      @JoinColumn(
          name = "user_id",
          referencedColumnName = "id",
          nullable = false,
          foreignKey = @ForeignKey(name = "fk_user_id")),
      inverseJoinColumns =
      @JoinColumn(
          name = "group_id",
          referencedColumnName = "id",
          nullable = false,
          foreignKey = @ForeignKey(name = "fk_group_id")))
  private Set<PermissionGroup> groups = new HashSet<>();

  public void addPermissionGroup(PermissionGroup group) {
    this.groups.add(group);
  }

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
    final User user = (User) obj;
    if (!this.email.equals(user.email)) {
      return false;
    }
    return this.username.equals(user.username);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + this.email.hashCode();
    result = 31 * result + this.username.hashCode();
    return result;
  }
}
