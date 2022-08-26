package org.hbrs.academicflow.util;

import com.vaadin.flow.component.UI;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hbrs.academicflow.control.student.StudentService;
import org.hbrs.academicflow.model.user.UserDTO;
import org.jetbrains.annotations.Nullable;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SessionAttributes {

  public static @Nullable UserDTO getCurrentUser() {
    try {
      return (UserDTO) UI.getCurrent().getSession().getAttribute(Constants.CURRENT_USER);
    } catch (NullPointerException e) {
      log.info("No current user found");
      return null;
    }
  }

  public static boolean isCurrentUserStudent(StudentService service) {
    // service should not be null
    if (service == null) {
      throw new NullPointerException("Service is null");
    }
    UserDTO userDto = getCurrentUser();
    if (userDto == null) {
      return false;
    }
    return service.findStudentByUserID(userDto.getId()) != null;
  }
}
