package org.hbrs.academicflow.model.profile;

import java.sql.Blob;
import javax.persistence.*;

@Entity
@Table(name = "profile", schema = "public")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id", unique = true, nullable = false)
    private int profile_id = -1;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinTable(name = "user", schema = "public", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false))
    private int user_id = -1;

    @Basic
    @Column(name = "description", nullable = true)
    private String description = "";

    @Basic
    @Column(name = "address", nullable = true)
    private String address = "";

    @Basic
    @Column(name = "docs", nullable = true)
    private Blob docs = null;
}
