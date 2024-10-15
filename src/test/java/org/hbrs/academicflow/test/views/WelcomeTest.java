package org.hbrs.academicflow.test.views;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import lombok.RequiredArgsConstructor;
import org.hbrs.academicflow.control.permission.PermissionGroupService;
import org.hbrs.academicflow.control.student.StudentService;
import org.hbrs.academicflow.control.user.UserMapperImpl;
import org.hbrs.academicflow.control.user.UserService;
import org.hbrs.academicflow.model.user.UserDTO;
import org.hbrs.academicflow.model.user.UserRepository;
import org.hbrs.academicflow.util.SessionAttributes;
import org.hbrs.academicflow.util.Statistics;
import org.hbrs.academicflow.view.routes.welcome.WelcomeView;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@SpringBootTest
class WelcomeTest {

  public static final String VAADIN_VERTICAL_LAYOUT = "  </vaadin-vertical-layout>\n";
  public static final String VAADIN_VERTICAL_LAYOUT1 = " </vaadin-vertical-layout>\n";
  public static final String VAADIN_HORIZONTAL_LAYOUT = "  </vaadin-horizontal-layout>\n";
  public static final String H_1 = "   <h1>";
  public static final String CLASS_CARD =
      "  <vaadin-vertical-layout theme=\"padding spacing\" style=\"width:100%;align-items:center\""
          + " class=\"card\">\n";
  public static final String STELLENANZEIGEN_LABEL = "   <label>Anzahl Stellenanzeigen</label>\n";
  public static final String UNTERNEHMEN_LABEL = "   <label>Registrierte Unternehmen</label>\n";
  public static final String BEWERBUNGEN_LABEL = "   <label>Eingegangene Bewerbungen</label>\n";
  public static final String STUDENTEN_LABEL = "   <label>Registrierte Studenten</label>\n";
  public static final String H1NL = "</h1>\n";
  // Junit tests for the Welcome view from routes.welcome.WelcomeView

  protected final StudentService studentService;
  protected final UserRepository userRepository;
  protected final UserService userService;
  @Autowired final PermissionGroupService permissionGroupService;
  private final Statistics stats;

  /** This function tests @Link{org.hbrs.academicflow.view.routes.welcome.WelcomeView} */
  @Test
  @Transactional
  void testWelcomeView() {
    // Check if View Builds
    WelcomeView welcomeView = new WelcomeView(stats);
    UserDTO studentUser =
        new UserMapperImpl().toDTO(userRepository.findUserByUsername("LauraStudent"));
    try (MockedStatic<SessionAttributes> utilities = Mockito.mockStatic(SessionAttributes.class)) {
      utilities.when(SessionAttributes::getCurrentUser).thenReturn(studentUser);
      assertNotNull(SessionAttributes.getCurrentUser());
      assertDoesNotThrow(welcomeView::doInitialSetup);
    }

    String divContent =
        "<div>\n"
            + " <vaadin-vertical-layout theme=\"padding spacing\" style=\"width:100%\">\n"
            + "  <h2>Willkommen bei Academic Flow, LauraStudent</h2>\n"
            + "  <vaadin-horizontal-layout theme=\"spacing\" style=\"width:80%\">\n"
            + "   <h2></h2>\n"
            + "   <img alt=\"WillkommenFoto\" src=\"images/WillkommenFoto.jpg\""
            + " style=\"width:15vw\" class=\"profile-picture\">\n"
            + "   <p style=\"font-size:var(--lumo-font-size-l)\">Wilkommen auf AcadamicFlow. Unser"
            + " Ziel ist es, DIE Jobbörse für Studenten zu werden. Wir haben viele spannende"
            + " Stellenanzeigen von den Top - Unternehmen. Wir bieten außerdem die Möglichkeit,"
            + " nach Tätigkeitsfeldern wie Informatik oder Maschinenbau zu suchen. Da ist sicher"
            + " auch für dich etwas dabei. Leg jetzt los und finde deinen Traumjob!</p>\n"
            + VAADIN_HORIZONTAL_LAYOUT
            + VAADIN_VERTICAL_LAYOUT1
            + " <vaadin-horizontal-layout theme=\"spacing\">\n"
            + CLASS_CARD
            + STELLENANZEIGEN_LABEL
            + H_1
            + stats.getAmountOfJobAds()
            + H1NL
            + VAADIN_VERTICAL_LAYOUT
            + CLASS_CARD
            + UNTERNEHMEN_LABEL
            + H_1
            + stats.getAmountOfCompanies()
            + H1NL
            + VAADIN_VERTICAL_LAYOUT
            + CLASS_CARD
            + BEWERBUNGEN_LABEL
            + H_1
            + stats.getAmountOfApplies()
            + H1NL
            + VAADIN_VERTICAL_LAYOUT
            + CLASS_CARD
            + STUDENTEN_LABEL
            + H_1
            + stats.getAmountOfStudents()
            + H1NL
            + VAADIN_VERTICAL_LAYOUT
            + " </vaadin-horizontal-layout>\n"
            + "</div>";
    assertEquals(divContent, welcomeView.getElement().toString());
  }
}
