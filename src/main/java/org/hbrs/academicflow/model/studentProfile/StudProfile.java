package org.hbrs.academicflow.model.studentProfile;


import com.helger.commons.annotation.OverrideOnDemand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hbrs.academicflow.model.studentUser.StudUser;

import javax.persistence.*;
import java.sql.Blob;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "stud_profile", schema = "public")
public class StudProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stud_profile", unique = true, nullable = false)
    private int stud_profile_id = -1;

    @Lob
    @Basic
    @Column(name = "profile_image", nullable = false)
    private Blob profile_image;

    @Basic
    @Column(name = "description", nullable = true)
    private String description = "";

    @Basic
    @Column(name = "adress", nullable = false)
    private String adress = "";

    @OneToOne
    @JoinColumn(name ="stud_user_id")
    private StudUser user;

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof StudProfile)) {
            return false;
        }
        StudProfile other = (StudProfile) obj;
        return this.stud_profile_id == other.stud_profile_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.stud_profile_id);
    }

}
