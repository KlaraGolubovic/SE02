package org.hbrs.academicflow.model.profile;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer>, Serializable {    
    //ToDo add additional querrys
}
