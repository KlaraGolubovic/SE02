package org.hbrs.academicflow.model.common;

import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

@Getter
@Setter
@ToString
@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseEntity {

  @Id
  @NotNull
  @Type(type = "pg-uuid")
  @ToString.Include(rank = -1)
  @Column(updatable = false, nullable = false)
  private UUID id = UUID.randomUUID();

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || this.getClass() != obj.getClass()) {
      return false;
    }
    final BaseEntity baseEntity = (BaseEntity) obj;
    return Objects.equals(this.id, baseEntity.id);
  }

  @Override
  public int hashCode() {
    return this.id != null ? this.id.hashCode() : 0;
  }
}
