package org.hbrs.academicflow.model.user;

import org.hbrs.academicflow.model.user.dto.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

import java.io.Serializable;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> , Serializable {
    @Query("select new org.hbrs.academicflow.model.user.dto.UserDTOImpl(user) from User user where user.occupation=:occupation")
    List<UserDTO> findUsersByOccupation(@Param("occupation") String occupation);

    @Query("select new org.hbrs.academicflow.model.user.dto.UserDTOImpl(user) from User user where user.userid=:userId and user.password=:password")
    UserDTO findUserByIdAndPassword(@Param("userId") String id, @Param("password") String password);

    @Query("select new org.hbrs.academicflow.model.user.dto.UserDTOImpl(user) from User user where user.userid=:userId")
    UserDTO findUserByUserId(@Param("userId") String userId);

    @Modifying
    @Transactional
    @Query("delete from User user where user.userid=:userId")
    void deleteUserByUserId(@Param("userId") String userId);
}