package org.hbrs.academicflow.model.user;

import java.io.Serializable;
import java.util.List;
import javax.transaction.Transactional;
import org.hbrs.academicflow.model.user.dto.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, Serializable {
  @Query(
      "select new org.hbrs.academicflow.model.user.dto.UserDTOImpl(user) from User user where"
          + " user.occupation=:occupation")
  List<UserDTO> findUsersByOccupation(@Param("occupation") String occupation);

  @Query(
      "select new org.hbrs.academicflow.model.user.dto.UserDTOImpl(user) from User user where"
          + " user.user_id=:user_id and user.password=:password")
  UserDTO findUserByIdAndPassword(@Param("user_id") String user_id, @Param("password") String password);

  @Query(
      "select new org.hbrs.academicflow.model.user.dto.UserDTOImpl(user) from User user where"
          + " user.username=:username")
  UserDTO findUserByUsername(@Param("username") String username);

  @Modifying
  @Transactional
  @Query("delete from User user where user.username=:username")
  void deleteUserByUsername(@Param("username") String username);
}