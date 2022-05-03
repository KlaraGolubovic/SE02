package org.hbrs.academicflow.model.companyProfile;



import java.sql.Blob;
import javax.persistence.*;

import org.hbrs.academicflow.model.companyUser.CompUser;

@Entity
@Table(name = "comp_profile", schema = "public")
public class CompProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comp_profile_id", unique = true, nullable = false)
    private int comp_profile_id = -1;

    @OneToOne
 //   @JoinTable(name = "user", schema = "public", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false))
    @JoinColumn(name = "user_id")
    private CompUser user;

    @Basic
    @Column(name = "description", nullable = true)
    private String description = "";

    @Basic
    @Column(name = "address", nullable = true)
    private String address = "";

    @Lob
    @Basic
    @Column(name = "docs", nullable = true)
    private Blob docs = null ;
}