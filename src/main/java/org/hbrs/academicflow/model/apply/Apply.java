package org.hbrs.academicflow.model.apply;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hbrs.academicflow.model.advertisement.Advertisement;
import org.hbrs.academicflow.model.common.BaseEntity;
import org.hbrs.academicflow.model.student.user.Student;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "apply", schema = "public")
public class Apply extends BaseEntity {

  @Nullable
  @Builder.Default
  @Column(name = "note", length = 1100)
  private String note = "";

  @NotNull
  @Builder.Default
  @Column(name = "applied")
  private Instant applied = Instant.now();

  @NotNull
  @ManyToOne(optional = false)
  @JoinColumn(
      name = "student",
      referencedColumnName = "id",
      foreignKey = @ForeignKey(name = "fk_student_id"))
  private Student student;

  @NotNull
  @ManyToOne(optional = false)
  @JoinColumn(
      name = "advertisement",
      referencedColumnName = "id",
      foreignKey = @ForeignKey(name = "fk_advertisement_id"))
  private Advertisement advertisement;

  public String getStudentName() {
    return this.student.getFirstName() + " " + this.student.getLastName();
  }

  public String getCompanyName() {
    return this.advertisement.getCompany().getName();
  }

  public String getAdvertisementTitle() {
    return this.advertisement.getTitle();
  }

  public String getFormattedDate() {
    DateTimeFormatter formatter =
        DateTimeFormatter.ofPattern("dd.MM.yyyy").withZone(ZoneId.systemDefault());
    return formatter.format(this.applied);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Apply)) {
      return false;
    }
    final Apply apply = (Apply) obj;
    return this.advertisement.equals(apply.advertisement)
        && this.applied.equals(apply.applied)
        && this.note.equals(apply.note)
        && this.student.equals(apply.student);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + this.student.hashCode();
    return result;
  }
}
