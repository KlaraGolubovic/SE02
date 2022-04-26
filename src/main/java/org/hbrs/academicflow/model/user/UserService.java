package org.hbrs.academicflow.model.user;

import lombok.RequiredArgsConstructor;
import org.hbrs.academicflow.model.user.dto.UserDTO;
import org.hbrs.academicflow.util.Encryption;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserService implements Serializable {
  private final UserRepository repository;

  @Nullable
  public UserDTO findUserByUsernameAndPassword(String username, String password) {
    final String encrypted;
    try {
      encrypted = Encryption.sha256(password);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      return null;
    }
    return this.repository.findUserByUsernameAndPassword(username, encrypted);
  }

  public UserDTO findUserByUsername(String username) {
    return this.repository.findUserByUsername(username);
  }

  public User doCreateUser(User user) {
    return this.repository.save(user);
  }

  public void deleteUser(String username) {
    this.repository.deleteUserByUsername(username);
  }

  public List<User> findAllUsers() {
    return this.repository.findAll();
  }
}
