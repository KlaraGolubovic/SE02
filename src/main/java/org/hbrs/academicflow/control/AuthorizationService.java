package org.hbrs.academicflow.control;

import org.hbrs.academicflow.model.user.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationService {

  /**
   * This function will check if the given user has the requested permission-group
   *
   * @param user who should be checked
   * @param name of the requested permission-group
   * @return boolean either true if the user has the required permission-group or false if not so
   */
  public boolean hasPermissionGroup(UserDTO user, String name) {
    return user.getGroups().stream().anyMatch(group -> group.getName().equals(name));
  }

  /**
   * This function will check if the given user has access to the requested content
   *
   * @param user who should be checked
   * @param permissionLevel which is required for the later action
   * @return boolean either true if the user is permitted or false if not so
   */
  public boolean isUserAllowed(UserDTO user, Integer permissionLevel) {
    return user.getGroups().stream().anyMatch(group -> group.getLevel() >= permissionLevel);
  }
}
