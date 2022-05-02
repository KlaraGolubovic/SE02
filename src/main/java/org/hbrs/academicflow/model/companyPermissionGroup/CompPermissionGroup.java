package org.hbrs.academicflow.model.companyPermissionGroup;


import java.util.List;
import java.util.Objects;

import javax.persistence.*;

import org.apache.commons.compress.utils.Lists;
import org.hbrs.academicflow.model.companyUser.CompUser;

@Entity
@Table(name="company_group", schema = "public")
public class CompPermissionGroup {
    @Id
    @Column(name = "name", unique = true, nullable = false)
    private String name = "";

    @Column(name = "level", nullable = false)
    private int level = -1;

    @ManyToMany(mappedBy = "groups", fetch = FetchType.EAGER)
    private List<CompUser> users = Lists.newArrayList();

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null) return false;
        CompPermissionGroup group = (CompPermissionGroup) obj;
        return Objects.equals(this, group) && this.name == group.name && this.level == group.level;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.level);
    }
}