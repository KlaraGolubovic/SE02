package org.hbrs.academicflow.model.user;

import java.util.List;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

  @Query("select user from User user where user.username=:username and user.password=:password")
  User findUserByUsernameAndPassword(
      @Param("username") String username, @Param("password") String password);

  @Query("select user from User user where user.username=:username")
  User findUserByUsername(@Param("username") String username);

  @Query("select (count(user.id) > 0) from User user where user.email=:email")
  boolean isEmailAlreadyInUse(@Param("email") String email);

  @Query("select (count(user.id) > 0) from User user where user.username=:username")
  boolean isUsernameAlreadyInUse(@Param("username") String username);

  @Modifying
  @Transactional
  @Query("delete from User user where user.username=:username")
  void deleteUserByUsername(@Param("username") String username);

  @Modifying
  @Transactional
  @Query("delete from User user where user.id in :userIds")
  void deleteUsersByIds(@Param("userIds") List<UUID> userIds);

  @Query("select user from User user where user.email=:email")
  User findUserByEmail(@Param("email") String email);
}