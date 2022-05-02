package org.hbrs.academicflow.model.student;


import javax.persistence.*;

@Entity
@Table(name = "stud_profile", schema = "public")
public class StudentProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int stud_profile_id = -1;

}
