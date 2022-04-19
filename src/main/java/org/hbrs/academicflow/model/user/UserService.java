package org.hbrs.academicflow.model.user;

import java.io.Serializable;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hbrs.academicflow.model.user.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService implements Serializable {
  private final UserRepository repository;

  public UserDTO findUserByIdAndPassword(String id, String password) {
    return this.repository.findUserByIdAndPassword(id, password);
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
