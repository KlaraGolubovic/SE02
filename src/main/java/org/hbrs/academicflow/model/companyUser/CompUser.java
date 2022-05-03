package org.hbrs.academicflow.model.companyUser;

import java.util.Objects;
import javax.persistence.*;
import lombok.*;
import org.hbrs.academicflow.model.user.User;

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
  @Column(name = "name", nullable = false)
  private String name = "";

  @Basic
  @Column(name = "phone")
  private String phone = "";


  @OneToOne
  @JoinColumn(name = "user_id")
  private User user;

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
