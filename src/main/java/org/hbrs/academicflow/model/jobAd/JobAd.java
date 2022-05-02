package org.hbrs.academicflow.model.jobAd;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "job_ad", schema = "public")
public class JobAd {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int job_id = -1;

    @Basic
    @Column(name = "title", nullable = false)
    private String title = "";

    @Basic
    @Column(name = "description", nullable = true)
    private LocalDate date = LocalDate.now();

    @Basic
    @Column(name = "job_type", nullable = false)
    private String jobType = "";

    @Basic
    @Column(name = "loaction", nullable = false)
    private String location = "";

/*
    @OneToOne
    @JoinColumn(name = "comp_user_id")
    private CompUser compUser;
*/
    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof JobAd)) {
            return false;
        }
        JobAd other = (JobAd) obj;
        return this.job_id == other.job_id;
    }
    






}
