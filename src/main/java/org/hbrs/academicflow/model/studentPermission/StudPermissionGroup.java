package org.hbrs.academicflow.model.studentPermission;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.compress.utils.Lists;
import org.hbrs.academicflow.model.studentUser.StudUser;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "student_group", schema = "public")
public class StudPermissionGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_name", unique = true, nullable = false)
    private String name = "";

    @ManyToMany(mappedBy = "groups", fetch = FetchType.EAGER)
    private List<StudUser> users = new ArrayList<>();

}
