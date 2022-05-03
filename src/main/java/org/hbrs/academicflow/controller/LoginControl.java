package org.hbrs.academicflow.controller;

import lombok.RequiredArgsConstructor;

import java.io.Serializable;

import org.hbrs.academicflow.controller.exception.DatabaseUserException;
import org.hbrs.academicflow.model.user.UserService;
import org.hbrs.academicflow.model.user.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LoginControl implements Serializable {
  private final UserService service;

  private UserDTO currentUser = null;

  public boolean doAuthenticate(String username, String password) throws DatabaseUserException {
    final UserDTO user = this.service.findUserByUsernameAndPassword(username, password);
    if (user == null) {
      throw new DatabaseUserException("User not found.");
    }
    this.currentUser = user;
    return true;
  }

  public UserDTO getCurrentUser() {
    return this.currentUser;
  }
}
