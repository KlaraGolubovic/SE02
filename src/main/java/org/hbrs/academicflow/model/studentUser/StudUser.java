package org.hbrs.academicflow.model.studentUser;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hbrs.academicflow.model.user.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Setter(AccessLevel.PUBLIC)
@Entity
@Table(name = "student_user", schema = "public")
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
    @Column(name = "phone", unique = true, nullable = true)
    private String phone = "";

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

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
