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
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService implements Serializable {
  private final UserRepository repository;

  @Nullable
  public UserDTO findUserByIdAndPassword(String id, String password) {
    final String encrypted;
    try {
      encrypted = Encryption.sha256(password);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      return null;
    }
    return this.repository.findUserByIdAndPassword(id, encrypted);
  }

  public UserDTO findUserById(String id) {
    return this.repository.findUserByUserId(id);
  }

  public User doCreateUser(User user) {
    return this.repository.save(user);
  }

  public void deleteUser(String userId) {
    this.repository.deleteUserByUserId(userId);
  }

  public List<User> findAllUsers() {
    return this.repository.findAll();
  }
}
