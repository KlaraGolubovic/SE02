package org.hbrs.academicflow.test.views;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.vaadin.flow.component.tabs.Tab;
import lombok.RequiredArgsConstructor;
import org.hbrs.academicflow.control.LoginService;
import org.hbrs.academicflow.control.company.user.CompanyService;
import org.hbrs.academicflow.control.student.StudentService;
import org.hbrs.academicflow.control.user.UserMapperImpl;
import org.hbrs.academicflow.control.user.UserService;
import org.hbrs.academicflow.model.user.UserDTO;
import org.hbrs.academicflow.model.user.UserRepository;
import org.hbrs.academicflow.util.SessionAttributes;
import org.hbrs.academicflow.view.common.components.TabGenerator;
import org.hbrs.academicflow.view.common.layouts.AppView;
import org.hbrs.academicflow.view.routes.advertisement.AdApplication;
import org.hbrs.academicflow.view.routes.advertisement.AdvertisementManagement;
import org.hbrs.academicflow.view.routes.advertisement.AdvertisementSearch;
import org.hbrs.academicflow.view.routes.advertisement.StudentApplicationView;
import org.hbrs.academicflow.view.routes.profile.ProfileView;
import org.hbrs.academicflow.view.routes.profile.StudentProfileView;
import org.hbrs.academicflow.view.routes.welcome.WelcomeView;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@SpringBootTest
class AppViewTest {

  protected final StudentService studentService;
  protected final CompanyService companyService;
  protected final UserRepository userRepository;
  protected final UserService userService;
  protected final LoginService loginService;

  /** Test the @Link{org.hbrs.academicflow.view.common.layouts.AppView} class. */
  @SuppressWarnings("java:S1192")
  // specific string could be changed depending on role, merge would be antipattern of "TDD" using a
  // refactor to the test as a fixation of design choice.
  @Test
  @Transactional
  void testAppViewForStudent() {
    // Mock sessionattributes
    UserDTO studentUser =
        new UserMapperImpl().toDTO(userRepository.findUserByUsername("LauraStudent"));
    try (MockedStatic<SessionAttributes> utilities = Mockito.mockStatic(SessionAttributes.class)) {
      utilities.when(SessionAttributes::getCurrentUser).thenReturn(studentUser);
      assertNotNull(SessionAttributes.getCurrentUser());
      assertDoesNotThrow(() -> new AppView(studentService, companyService, loginService));
      try (MockedStatic<TabGenerator> tg = Mockito.mockStatic(TabGenerator.class)) {
        tg.when(() -> TabGenerator.buildTab("Startseite", WelcomeView.class))
            .thenReturn(new Tab("Startseite"));
        tg.when(() -> TabGenerator.buildTab("Mein Profil", StudentProfileView.class))
            .thenReturn(new Tab("Mein Profil"));
        tg.when(() -> TabGenerator.buildTab("Meine Bewerbungen", StudentApplicationView.class))
            .thenReturn(new Tab("Meine Bewerbungen"));
        tg.when(() -> TabGenerator.buildTab("Alle Jobanzeigen", AdvertisementSearch.class))
            .thenReturn(new Tab("Anzeigen"));
        assertDoesNotThrow(() -> new AppView(studentService, companyService, loginService).init());
      }
    }
  }

  @SuppressWarnings("java:S1192")
  @Test
  @Transactional
  void testAppViewForOrganisation() {
    // Mock sessionattributes
    UserDTO organisationUser =
        new UserMapperImpl().toDTO(userRepository.findUserByUsername("OrgaPaul"));
    try (MockedStatic<SessionAttributes> utilities = Mockito.mockStatic(SessionAttributes.class)) {
      utilities.when(SessionAttributes::getCurrentUser).thenReturn(organisationUser);
      assertNotNull(SessionAttributes.getCurrentUser());
      assertDoesNotThrow(() -> new AppView(studentService, companyService, loginService));
      try (MockedStatic<TabGenerator> tg = Mockito.mockStatic(TabGenerator.class)) {
        tg.when(() -> TabGenerator.buildTab("Startseite", WelcomeView.class))
            .thenReturn(new Tab("Startseite"));
        tg.when(() -> TabGenerator.buildTab("Unternehmensprofil", ProfileView.class))
            .thenReturn(new Tab("Unternehmensprofil"));
        tg.when(() -> TabGenerator.buildTab("Meine Jobanzeigen", AdvertisementManagement.class))
            .thenReturn(new Tab("Meine Anzeigen"));
        tg.when(() -> TabGenerator.buildTab("Eingegangene Bewerbungen", AdApplication.class))
            .thenReturn(new Tab("Eingegangene Bewerbungen"));
        tg.when(() -> TabGenerator.buildTab("Alle Jobanzeigen", AdvertisementSearch.class))
            .thenReturn(new Tab("Anzeigen"));
        assertDoesNotThrow(() -> new AppView(studentService, companyService, loginService).init());
      }
    }
  }
}
