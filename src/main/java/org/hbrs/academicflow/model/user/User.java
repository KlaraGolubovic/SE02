package org.hbrs.academicflow.model.user;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hbrs.academicflow.model.permission.PermissionGroup;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Setter(AccessLevel.PUBLIC)
@Getter
@Entity
@Table(name = "user", schema = "backend")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id", unique = true, nullable = false)
  private int id = -1;

  @Basic
  @Column(name = "date_of_birth", nullable = false)
  private LocalDate dateOfBirth = LocalDate.now();

  @Basic
  @Column(name = "email", unique = true, nullable = false)
  private String email = "";

  @Basic
  @Column(name = "first_name", nullable = false)
  private String firstName = "";

  @Basic
  @Column(name = "last_name", nullable = false)
  private String lastName = "";

  @Basic
  @Column(name = "occupation")
  private String occupation = "";

  @Basic
  @Column(name = "password", nullable = false)
  private String password = "";

  @Basic
  @Column(name = "phone")
  private String phone = "";

  @Basic
  @Column(name = "username", nullable = false)
  private String username = "";

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "user_group",
      schema = "backend",
      joinColumns =
          @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false),
      inverseJoinColumns =
          @JoinColumn(name = "group_name", referencedColumnName = "name", nullable = false))
  private List<PermissionGroup> groups = new ArrayList<>();

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || this.getClass() != obj.getClass()) {
      return false;
    }
    final User user = (User) obj;
    return this.id == user.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id);
  }
}
