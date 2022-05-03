package org.hbrs.academicflow.model.studentProfile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudProfileRepository extends JpaRepository<StudProfile, Integer> {

}
