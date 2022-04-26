package org.hbrs.academicflow.model.user.dto;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hbrs.academicflow.model.permission.PermissionGroup;
import org.hbrs.academicflow.model.permission.dto.PermissionGroupDTO;
import org.hbrs.academicflow.model.permission.dto.PermissionGroupDTOImpl;
import org.hbrs.academicflow.model.user.User;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTOImpl implements UserDTO {
  private int user_id;
  private String firstName;
  private String lastName;
  private List<PermissionGroupDTO> groups;

  public UserDTOImpl(User user) {
    this.user_id = user.getUser_id();
    this.firstName = user.getFirstName();
    this.lastName = user.getLastName();
    this.groups =
        user.getGroups().stream()
            .map(PermissionGroup::getName)
            .map(PermissionGroupDTOImpl::new)
            .collect(Collectors.toList());
  }

  @Override
  public String toString() {
    return "UserDTOImpl{"
        + "user_id="
        + user_id
        + ", firstname='"
        + firstName
        + '\''
        + ", lastname='"
        + lastName
        + '\''
        + ", roles="
        + groups
        + '}';
  }

  @Override
  public int getId() {
    return user_id;
  }
}
