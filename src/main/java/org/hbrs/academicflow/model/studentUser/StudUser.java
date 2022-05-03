package org.hbrs.academicflow.model.studentUser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hbrs.academicflow.model.permission.PermissionGroup;
import org.hbrs.academicflow.model.studentPermission.StudPermissionGroup;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "studentUser", schema = "public")
public class StudUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stud_user_id", unique = true, nullable = false)
    private int stud_user_id = -1;

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
    @Column(name = "occupation", nullable = true)
    private String occupation = "";

    @Basic
    @Column(name = "password", nullable = false)
    private String password = "";

    @Basic
    @Column(name = "phone", unique = true, nullable = true)
    private String phone = "";

    @Basic
    @Column(name = "username", unique = true, nullable = false)
    private String username = "";

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_group", // Name der Zwischentabelle: ToDo: tbd: zwei Zwischentabellen permission_student und company_group ?
            schema = "public",
            joinColumns = @JoinColumn( // FK1 owner (StudUser)
                    name = "stud_user_id",
                    referencedColumnName = "stud_user_id",
                    nullable = false),
            inverseJoinColumns = @JoinColumn( // FK2 other (StudPermissionGroup)
                    name = "name",
                    referencedColumnName = "name",
                    nullable = false)
    )
    List<PermissionGroup> groups = new ArrayList<>();

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof StudUser)) {
            return false;
        }
        StudUser other = (StudUser) obj;
        return this.stud_user_id == other.stud_user_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.stud_user_id);
    }

}
