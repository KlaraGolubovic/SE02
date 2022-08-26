package org.hbrs.academicflow.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import lombok.RequiredArgsConstructor;
import org.hbrs.academicflow.test.common.DefaultValues;
import org.hbrs.academicflow.view.routes.advertisement.component.EditableAdvertisement;
import org.hbrs.academicflow.view.routes.advertisement.component.VisibleAd;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class VisibleAdTest {

  public static final String VAADIN_VERTICAL_LAYOUT = "  </vaadin-vertical-layout>\n";
  private static final String VAADINBUTTON = "   </vaadin-button>\n";
  private static final String VAADINBUTTONWITHTAB = "   <vaadin-button>\n";
  private static final Object VISIBLE = "<div style=\"width:100%\" class=\"outer\">\n"
      + " <vaadin-horizontal-layout style=\"width:100%;justify-content:flex-start\">\n"
      + "  <vaadin-vertical-layout theme=\"padding spacing\" style=\"width:100%\">\n"
      + "   <span><h2>Werkstudent/Praktikant Softwareentwicklung (m/w/d)</h2></span>\n"
      + "   <vaadin-horizontal-layout theme=\"spacing\">\n" + "    <span>Unternehmen: </span>\n"
      + "    <a href=\"organizationprofileStudentView/"
      + DefaultValues.DEFAULT_ADVERTISEMENT.getCompany().getId()
      + "\" style=\"color:var(--branding-dark-blue)\">Exxeta AG</a>\n"
      + "   </vaadin-horizontal-layout>\n" + "   <span>Job-Typ: Werkstudent</span>\n"
      + "   <span> Remote: Ja</span>\n" + "   <vaadin-details>\n" + "    <div>\n"
      + "     <p>Bei Exxeta fordern wir das traditionelle Konzept von Beratung und Tech heraus."
      + " Über 1000 Kolleg:innen an verschiedenen Standorten schaffen jeden Tag gemeinsam"
      + " digitale Lösungen, verändern Märkte und Mindsets – angetrieben von unserer"
      + " Leidenschaft für Technologie, unserem Teamspirit und dem Drang, echten Impact zu"
      + " schaffen. Hightech with a heartbeat eben. Du bist interessiert an Softwareentwicklung"
      + " und hast bereits erste Coding-Erfahrung? Du weißt noch nicht genau, welche Rolle in"
      + " der Entwicklung am besten zu dir passt? Dann komm zu Exxeta und wir finden es"
      + " gemeinsam heraus. Gestalte dein Studium zusammen mit uns! Bringe dich mit neuen Ideen"
      + " und Themen sowie deinen Stärken ein. Wir bieten keine festgeschriebene Position,"
      + " sondern die Möglichkeit deine Rolle im Unternehmen selbst zu finden oder eine neue"
      + " Rolle zu schaffen. Wähle selbst, ob du im Frontend oder Backend tätig sein"
      + " willst.</p>\n" + "    </div>\n"
      + "    <span slot=\"summary\">Beschreibung anzeigen</span>\n" + "   </vaadin-details>\n"
      + VAADIN_VERTICAL_LAYOUT + "  <vaadin-vertical-layout theme=\"padding spacing\""
      + " style=\"width:40%;align-items:stretch\">\n" + "   <span></span>\n"
      + "   <img alt=\"Unternehmensbild\" src=\"images/corporation-profile.png\""
      + " style=\"width:100%\" class=\"profile-picture\">\n" + VAADINBUTTONWITHTAB
      + "    Anzeige ansehen\n" + VAADINBUTTON + VAADINBUTTONWITHTAB + "    Profil von Exxeta AG \n"
      + VAADINBUTTON + VAADIN_VERTICAL_LAYOUT + " </vaadin-horizontal-layout>\n" + "</div>";
  private static final Object EDITABLE = "<div style=\"width:100%\" class=\"outer\">\n"
      + " <vaadin-horizontal-layout style=\"width:100%;justify-content:flex-start\">\n"
      + "  <vaadin-vertical-layout theme=\"padding spacing\" style=\"width:100%\">\n"
      + "   <span><h2>Werkstudent/Praktikant Softwareentwicklung (m/w/d)</h2></span>\n"
      + "   <vaadin-horizontal-layout theme=\"spacing\">\n" + "    <span>Unternehmen: </span>\n"
      + "    <a href=\"organizationprofileStudentView/"
      + DefaultValues.DEFAULT_ADVERTISEMENT.getCompany().getId()
      + "\" style=\"color:var(--branding-dark-blue)\">Exxeta AG</a>\n"
      + "   </vaadin-horizontal-layout>\n" + "   <span>Job-Typ: Werkstudent</span>\n"
      + "   <span> Remote: Ja</span>\n" + "   <vaadin-details>\n" + "    <div>\n"
      + "     <p>Bei Exxeta fordern wir das traditionelle Konzept von Beratung und Tech heraus."
      + " Über 1000 Kolleg:innen an verschiedenen Standorten schaffen jeden Tag gemeinsam"
      + " digitale Lösungen, verändern Märkte und Mindsets – angetrieben von unserer"
      + " Leidenschaft für Technologie, unserem Teamspirit und dem Drang, echten Impact zu"
      + " schaffen. Hightech with a heartbeat eben. Du bist interessiert an Softwareentwicklung"
      + " und hast bereits erste Coding-Erfahrung? Du weißt noch nicht genau, welche Rolle in"
      + " der Entwicklung am besten zu dir passt? Dann komm zu Exxeta und wir finden es"
      + " gemeinsam heraus. Gestalte dein Studium zusammen mit uns! Bringe dich mit neuen Ideen"
      + " und Themen sowie deinen Stärken ein. Wir bieten keine festgeschriebene Position,"
      + " sondern die Möglichkeit deine Rolle im Unternehmen selbst zu finden oder eine neue"
      + " Rolle zu schaffen. Wähle selbst, ob du im Frontend oder Backend tätig sein"
      + " willst.</p>\n" + "    </div>\n"
      + "    <span slot=\"summary\">Beschreibung anzeigen</span>\n" + "   </vaadin-details>\n"
      + VAADIN_VERTICAL_LAYOUT + "  <vaadin-vertical-layout theme=\"padding spacing\""
      + " style=\"width:40%;align-items:stretch\">\n" + "   <span></span>\n"
      + "   <img alt=\"Unternehmensbild\" src=\"images/corporation-profile.png\""
      + " style=\"width:100%\" class=\"profile-picture\">\n" + VAADINBUTTONWITHTAB
      + "    Bearbeiten \n" + VAADINBUTTON + VAADINBUTTONWITHTAB + "    Profil von Exxeta AG \n"
      + VAADINBUTTON + VAADIN_VERTICAL_LAYOUT + " </vaadin-horizontal-layout>\n" + "</div>";

  @Test
  void testContentsForVisibleAd() {
    VisibleAd va = new VisibleAd(DefaultValues.DEFAULT_ADVERTISEMENT);
    assertEquals(1, va.getChildren().count());
  }

  @Test
  void testBorder() {
    VisibleAd va = new VisibleAd(DefaultValues.DEFAULT_ADVERTISEMENT);
    assertEquals(VISIBLE, va.getElement().getOuterHTML());
  }

  @Test
  void testTitleVisible() {
    EditableAdvertisement va = new EditableAdvertisement(DefaultValues.DEFAULT_ADVERTISEMENT);
    assertEquals(EDITABLE, va.getElement().getOuterHTML());
  }
}
