package org.hbrs.academicflow.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.vaadin.flow.component.UI;
import lombok.RequiredArgsConstructor;
import org.hbrs.academicflow.model.user.UserDTO;
import org.hbrs.academicflow.util.SessionAttributes;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@SpringBootTest
class SessionAttributesTest {

  /** This function tests @Link{org.hbrs.academicflow.util.SessionAttributes} */
  @Test
  @Transactional
  void testSessionAttributes() {
    UI mockedUI = Mockito.mock(UI.class, Answers.RETURNS_DEEP_STUBS);
    Mockito.when(mockedUI.getSession().getAttribute("current_User")).thenReturn(new UserDTO());
    try (MockedStatic<UI> utilities = Mockito.mockStatic(UI.class)) {
      utilities.when(UI::getCurrent).thenReturn(mockedUI);
      assertNotNull(SessionAttributes.getCurrentUser());
    }
  }

  /**
   * This function tests @Link{org.hbrs.academicflow.util.SessionAttributes} and checks weather the
   * current user is null or not using mockito.
   */
  @Test
  @Transactional
  void testSessionAttributesNull() {
    UI mockedUI = Mockito.mock(UI.class, Answers.RETURNS_DEEP_STUBS);
    Mockito.when(mockedUI.getSession().getAttribute("current_User")).thenReturn(null);
    try (MockedStatic<UI> utilities = Mockito.mockStatic(UI.class)) {
      utilities.when(UI::getCurrent).thenReturn(mockedUI);
      assertNull(SessionAttributes.getCurrentUser());
    }
  }
}
