package org.hbrs.academicflow.model.student.user;

import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hbrs.academicflow.model.common.BaseEntity;
import org.hbrs.academicflow.model.user.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Setter
@Entity
@Table(name = "student", schema = "public")
public class Student extends BaseEntity {

  @NotNull
  @Builder.Default
  @Column(name = "date_of_birth", nullable = false)
  private Instant dateOfBirth = Instant.now();
  @NotNull
  @Builder.Default
  @Column(name = "first_name", nullable = false)
  private String firstName = "";
  @NotNull
  @Builder.Default
  @Column(name = "last_name", nullable = false)
  private String lastName = "";
  @Nullable
  @Builder.Default
  @Column(name = "phone")
  private String phone = "";
  // UNIQUE UND NULLABLE GEHT NICHT.
  @NotNull
  @OneToOne(orphanRemoval = true, optional = false)
  @JoinColumn(
      name = "user_id",
      referencedColumnName = "id",
      foreignKey = @ForeignKey(name = "fk_user_id"))
  private User user;

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
    final Student student = (Student) obj;
    return this.user.equals(student.user);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + this.user.hashCode();
    return result;
  }
}
