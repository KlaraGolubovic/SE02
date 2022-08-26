package org.hbrs.academicflow.control.user;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hbrs.academicflow.control.UserValidation;
import org.hbrs.academicflow.control.exception.DatabaseUserException;
import org.hbrs.academicflow.model.user.User;
import org.hbrs.academicflow.model.user.UserDTO;
import org.hbrs.academicflow.model.user.UserRepository;
import org.hbrs.academicflow.util.Encryption;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserService {

  private final UserMapper mapper;
  private final UserRepository repository;

  public @Nullable UserDTO findByUsernameAndPassword(
      @NotNull String username, @NotNull String password) {
    final User user =
        this.repository.findUserByUsernameAndPassword(username, Encryption.sha256(password));
    if (user == null) {
      return null;
    }
    return this.mapper.toDTO(user);
  }

  public @Nullable User findByUsername(@NotNull String username) {
    return this.repository.findUserByUsername(username);
  }

  public @Nullable User updateUser(@NotNull User user) {
    return this.repository.save(user);
  }

  public @Nullable User createUser(@NotNull User user) throws DatabaseUserException {
    if (!this.validateUser(user)) {
      throw new DatabaseUserException("Wrong Email");
    }
    return this.repository.save(user);
  }

  public void deleteByUsername(@NotNull String username) {
    this.repository.deleteUserByUsername(username);
  }

  public void deleteByIds(@NotNull List<UUID> userIds) {
    this.repository.deleteUsersByIds(userIds);
  }

  public void deleteById(@NotNull UUID userIds) {
    this.repository.deleteById(userIds);
  }

  public @NotNull List<User> findAllUsers() {
    return this.repository.findAll();
  }

  public User findUserByEmail(@NotNull String email) {
    return this.repository.findUserByEmail(email);
  }

  /**
   * This function is supposed to validate the given user information. In this case the email of the
   * given {@link User} should be checked on existance and correctness
   *
   * @param user who should be validated
   * @return boolean either true if everything is fine or false if there are some issues related to
   * the given {@link User}
   */
  public boolean validateUser(@NotNull User user) {
    if (UserValidation.isNotValidMailAddress(user.getEmail())) {
      log.error("Email of User with Id={} is invalid", user.getId());
      return false;
    }
    if (UserValidation.isNotValidUsername(user.getUsername())) {
      log.error("Username of User with Id={} is invalid", user.getId());
      return false;
    }
    if (this.repository.isUsernameAlreadyInUse(user.getUsername())) {
      log.error("Username of User with Id={} is invalid", user.getId());
      return false;
    }
    if (this.repository.isEmailAlreadyInUse(user.getEmail())) {
      log.info("Given Email of User with Id={} is valid but existent", user.getId());
      return false;
    }
    log.info("Given user is valid");
    return true;
  }
}
