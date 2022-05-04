package org.hbrs.academicflow.model.studentUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudUserRepository extends JpaRepository<StudUser, Integer> {

}
