package org.hbrs.academicflow.model.studentUser;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
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
