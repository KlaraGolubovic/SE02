package org.hbrs.appname.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "user", schema = "carlook")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id = -1;

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
    @Column(name = "occupation", nullable = false)
    private String occupation = "";

    @Basic
    @Column(name = "password", nullable = false)
    private String password = "";

    @Basic
    @Column(name = "phone", nullable = false)
    private String phone = "";

    @Basic
    @Column(name = "userid", nullable = false)
    private String userid = "";

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_to_rolle",
            catalog = "demouser",
            schema = "carlook",
            joinColumns = @JoinColumn(
                    name = "userid",
                    referencedColumnName = "id",
                    nullable = false
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "bezeichnung",
                    referencedColumnName = "bezeichhnung",
                    nullable = false
            )
    )
    private List<Rolle> roles;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        User user = (User) obj;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
