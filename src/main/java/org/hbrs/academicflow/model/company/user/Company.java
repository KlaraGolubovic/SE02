package org.hbrs.academicflow.model.company.user;

import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hbrs.academicflow.model.advertisement.Advertisement;
import org.hbrs.academicflow.model.common.BaseEntity;
import org.hbrs.academicflow.model.user.User;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(
    name = "company",
    schema = "public",
    indexes = {
        @Index(name = "idx_name", columnList = "name", unique = true),
        @Index(name = "idx_phone", columnList = "phone"),
    })
public class Company extends BaseEntity {

  @OneToMany(mappedBy = "company", orphanRemoval = true)
  Collection<Advertisement> advertisements;
  @NotNull
  @OneToOne(orphanRemoval = true, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(
      name = "user_id",
      referencedColumnName = "id",
      foreignKey = @ForeignKey(name = "fk_user_id"))
  private User user;
  @NotNull
  @Builder.Default
  @Column(name = "name", nullable = false)
  private String name = "";
  @Nullable
  @Builder.Default
  @Column(name = "phone")
  private String phone = "";

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Company)) {
      return false;
    }
    return this.user.equals(((Company) obj).user);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + this.name.hashCode();
    result = 31 * result + this.user.hashCode();
    return result;
  }
}
