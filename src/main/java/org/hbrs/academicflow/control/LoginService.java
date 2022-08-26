package org.hbrs.academicflow.control;

import lombok.RequiredArgsConstructor;
import org.hbrs.academicflow.control.exception.DatabaseUserException;
import org.hbrs.academicflow.control.user.UserService;
import org.hbrs.academicflow.model.user.UserDTO;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class LoginService {

  private final UserService userService;
  private UserDTO currentUser = null;

  public boolean authentication(@NotNull String username, @NotNull String password)
      throws DatabaseUserException {
    final UserDTO user = this.userService.findByUsernameAndPassword(username, password);
    if (user == null) {
      throw new DatabaseUserException("User not found.");
    }
    this.currentUser = user;
    return true;
  }

  public @Nullable UserDTO getCurrentUser() {
    return this.currentUser;
  }

  public void logout() {
    this.currentUser = null;
  }
}
