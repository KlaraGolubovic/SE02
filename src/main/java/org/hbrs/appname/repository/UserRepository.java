package org.hbrs.appname.repository;

import org.hbrs.appname.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select user from User user where user.occupation=:occupation")
    List<User> findUsersByOccupation(@Param("occupation") String occupation);

    @Query("select user from User user where user.id=:id and user.password=:password")
    User findUserByIdAndPassword(@Param("id") String id, @Param("password") String password);
}