package org.hbrs.academicflow.test.views;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.vaadin.flow.component.dialog.Dialog;
import lombok.RequiredArgsConstructor;
import org.hbrs.academicflow.control.LoginService;
import org.hbrs.academicflow.control.advertisement.AdvertisementService;
import org.hbrs.academicflow.control.apply.ApplyService;
import org.hbrs.academicflow.control.company.profile.CompanyProfileService;
import org.hbrs.academicflow.control.company.user.CompanyService;
import org.hbrs.academicflow.control.location.LocationMapper;
import org.hbrs.academicflow.control.location.LocationService;
import org.hbrs.academicflow.control.permission.PermissionGroupService;
import org.hbrs.academicflow.control.rating.RatingService;
import org.hbrs.academicflow.control.student.StudentService;
import org.hbrs.academicflow.control.student.profile.StudentProfileService;
import org.hbrs.academicflow.control.user.UserService;
import org.hbrs.academicflow.test.common.DefaultValues;
import org.hbrs.academicflow.util.Statistics;
import org.hbrs.academicflow.view.common.components.Navbar;
import org.hbrs.academicflow.view.common.components.VerticalSpacerGenerator;
import org.hbrs.academicflow.view.common.layouts.AppView;
import org.hbrs.academicflow.view.common.layouts.PublicAppView;
import org.hbrs.academicflow.view.routes.account.AccountView;
import org.hbrs.academicflow.view.routes.account.component.EditAccount;
import org.hbrs.academicflow.view.routes.advertisement.component.EditableAdvertisement;
import org.hbrs.academicflow.view.routes.backend.BackendDevelopmentView;
import org.hbrs.academicflow.view.routes.backend.component.ApplicationListViewer;
import org.hbrs.academicflow.view.routes.backend.component.DemoDummyDataCreator;
import org.hbrs.academicflow.view.routes.backend.component.DummyUserForm;
import org.hbrs.academicflow.view.routes.backend.component.OrganisationViewer;
import org.hbrs.academicflow.view.routes.backend.component.PermissionGroupAdministration;
import org.hbrs.academicflow.view.routes.backend.component.StudentViewer;
import org.hbrs.academicflow.view.routes.profile.ProfileView;
import org.hbrs.academicflow.view.routes.profile.component.EditOrganisationProfile;
import org.hbrs.academicflow.view.routes.profile.component.EditStudentProfile;
import org.hbrs.academicflow.view.routes.registration.RegistrationView;
import org.hbrs.academicflow.view.routes.welcome.WelcomeView;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class ViewBuildabilityTest {

  private static final String DISABLEDTF =
      "    <vaadin-text-field colspan=\"1\" disabled></vaadin-text-field>\n";
  private static final String REGISTRATIONHTML =
      "<div class=\"registration-view\">\n"
          + " <div>\n"
          + "  <vaadin-vertical-layout theme=\"padding spacing\" style=\"width:100%\">\n"
          + "   <span><h1>Registrieren</h1></span>\n"
          + "   <vaadin-form-layout>\n"
          + "    <vaadin-text-field colspan=\"1\" id=\"usernameField\"></vaadin-text-field>\n"
          + "    <vaadin-password-field colspan=\"1\"></vaadin-password-field>\n"
          + DISABLEDTF
          + DISABLEDTF
          + "    <vaadin-email-field colspan=\"1\"></vaadin-email-field>\n"
          + DISABLEDTF
          + "    <vaadin-select colspan=\"1\">\n"
          + "     <vaadin-list-box>\n"
          + "      <vaadin-item value=\"2\">\n"
          + "       Organisation\n"
          + "      </vaadin-item>\n"
          + "      <vaadin-item value=\"3\">\n"
          + "       Student\n"
          + "      </vaadin-item>\n"
          + "     </vaadin-list-box>\n"
          + "    </vaadin-select>\n"
          + "    <span></span>\n"
          + "    <vaadin-button colspan=\"1\" theme=\"primary\" id=\"registerFormSubmit\">\n"
          + "     Registrieren\n"
          + "    </vaadin-button>\n"
          + "    <vaadin-button colspan=\"1\">\n"
          + "     Bereits registriert? Hier anmelden!\n"
          + "    </vaadin-button>\n"
          + "   </vaadin-form-layout>\n"
          + "  </vaadin-vertical-layout>\n"
          + " </div>\n"
          + "</div>";
  private final UserService userService;
  private final StudentService studentService;
  private final StudentProfileService studentProfileService;
  private final CompanyService companyService;
  private final CompanyProfileService companyProfileService;
  private final PermissionGroupService permissionGroupService;
  private final LocationService locationService;
  private final RatingService ratingService;
  private final LocationMapper locationMapper;
  private final AdvertisementService advertisementService;
  private final ApplyService applyService;
  private final DummyUserForm dummyUserForm;
  private final StudentViewer studentViewer;
  private final PermissionGroupAdministration administration;
  private final ApplicationListViewer applyList;
  private final Statistics stats;

  @Test
  void contextLoads() {
    RegistrationView registrationView =
        new RegistrationView(userService, studentService, permissionGroupService, companyService);
    assertNotNull(registrationView);
    registrationView.doInitialSetup();
    assertEquals(REGISTRATIONHTML, registrationView.getElement().getOuterHTML());
  }

  @Test
  void testBackendView() {
    assertDoesNotThrow(
        () ->
            new BackendDevelopmentView(
                dummyUserForm,
                studentViewer,
                administration,
                applyList,
                new OrganisationViewer(companyService, companyProfileService, advertisementService),
                new DemoDummyDataCreator(
                    permissionGroupService,
                    studentService,
                    companyService,
                    userService,
                    advertisementService,
                    applyService,
                    studentProfileService,
                    companyProfileService,
                    locationService,
                    ratingService)));
    assertDoesNotThrow(
        () -> dummyUserForm.buildUserEditDialogLayout(new Dialog(), DefaultValues.DEFAULT_USER));
  }

  @Test
  void testAccountView() {
    EditAccount editAccount = new EditAccount(studentService);
    AccountView accountView = new AccountView(editAccount);
    assertNotNull(accountView);
  }

  @Test
  void testSEPView() {
    EditStudentProfile editStudentProfile =
        new EditStudentProfile(
            locationMapper, studentService, locationService, studentProfileService);
    assertNotNull(editStudentProfile);
  }

  @Test
  void testAppView() {
    AppView appView = new AppView(studentService, companyService, new LoginService(userService));
    assertNotNull(appView);
    assertDoesNotThrow(appView::init);
  }

  @Test
  void testEditableAdvertisement() {
    EditableAdvertisement ea = new EditableAdvertisement(DefaultValues.DEFAULT_ADVERTISEMENT);
    assertNotNull(ea);
  }

  @Test
  void testConstructorsDoNotThrow() {
    assertDoesNotThrow(
        () -> {
          new Navbar().getNavbar();
          EditOrganisationProfile ep =
              new EditOrganisationProfile(
                  locationMapper, companyService, locationService, companyProfileService);
          new ProfileView(ep).doInitialSetup();
          new PublicAppView();
          new VerticalSpacerGenerator("2vh").buildVerticalSpacer();
          new WelcomeView(stats);
        });
  }
}
