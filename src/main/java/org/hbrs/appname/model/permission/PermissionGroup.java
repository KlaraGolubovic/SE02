package org.hbrs.appname.model.permission;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hbrs.appname.model.user.User;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "permission_group", schema = "academic_flow")
public class PermissionGroup {
    @Id
    @Column(name = "name", unique = true, nullable = false, columnDefinition = "VARCHAR(36) DEFAULT 'GroupName'")
    private String name;

    @ManyToMany(mappedBy = "groups", fetch = FetchType.LAZY)
    private List<User> users;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        final PermissionGroup group = (PermissionGroup) obj;
        return Objects.equals(this.name, group.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }
}
