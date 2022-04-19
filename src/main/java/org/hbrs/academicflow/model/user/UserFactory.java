package org.hbrs.academicflow.model.user;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hbrs.academicflow.model.user.dto.UserDTO;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserFactory {
  public static User createUser(UserDTO dto) {
    final User user = new User();
    user.setEmail("test" + Math.random() + "@test.de");
    user.setFirstName(dto.getFirstName());
    user.setLastName(dto.getLastName());
    return user;
  }
}
