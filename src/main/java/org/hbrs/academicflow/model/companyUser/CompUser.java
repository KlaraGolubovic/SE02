package org.hbrs.academicflow.model.companyUser;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;
import lombok.*;
import org.hbrs.academicflow.model.permission.PermissionGroup;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Setter(AccessLevel.PUBLIC)
@Getter
@Entity
@Table(name = "company_user", schema = "public")
public class CompUser {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "company_user_id", unique = true, nullable = false)
  private int company_user_id = -1;

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
      schema = "public",
      joinColumns =
          @JoinColumn(
              name = "company_user_id",
              referencedColumnName = "company_user_id",
              nullable = false),
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
    final CompUser user = (CompUser) obj;
    return this.company_user_id == user.company_user_id;
  }


  @Override
  public int hashCode() {
    return Objects.hash(this.company_user_id);
  }
}
