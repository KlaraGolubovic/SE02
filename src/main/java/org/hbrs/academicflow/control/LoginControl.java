package org.hbrs.academicflow.control;

import lombok.RequiredArgsConstructor;
import org.hbrs.academicflow.control.exception.DatabaseUserException;
import org.hbrs.academicflow.model.user.UserService;
import org.hbrs.academicflow.model.user.dto.UserDTO;
import org.hbrs.academicflow.util.Encryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LoginControl {
  private final UserService service;

  private UserDTO currentUser = null;

  public boolean doAuthenticate(String username, String password) throws DatabaseUserException {
    final String encrypted;
    try {
      encrypted = Encryption.sha256(password);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      return false;
    }
    final UserDTO user = this.service.findUserByIdAndPassword(username, encrypted);
    if (user == null) {
      return false;
    }
    this.currentUser = user;
    return true;
  }

  public UserDTO getCurrentUser() {
    return this.currentUser;
  }
}
