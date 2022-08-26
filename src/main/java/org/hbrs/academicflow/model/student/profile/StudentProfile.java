package org.hbrs.academicflow.model.student.profile;

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
import org.hbrs.academicflow.model.location.Location;
import org.hbrs.academicflow.model.student.user.Student;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "student_profile", schema = "public")
public class StudentProfile extends BaseEntity {

  @NotNull
  @Column(name = "image", nullable = false)
  private Integer image;
  @Nullable
  @Builder.Default
  @Column(name = "description")
  private String description = "";
  @NotNull
  @OneToOne(optional = false)
  @JoinColumn(
      name = "location_id",
      referencedColumnName = "id",
      foreignKey = @ForeignKey(name = "fk_location_id"))
  private Location location;
  @NotNull
  @OneToOne(orphanRemoval = true, optional = false)
  @JoinColumn(
      name = "student_id",
      referencedColumnName = "id",
      foreignKey = @ForeignKey(name = "fk_student_id"))
  private Student student;

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
    final StudentProfile profile = (StudentProfile) obj;
    return this.student.equals(profile.student);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + this.student.hashCode();
    return result;
  }
}
