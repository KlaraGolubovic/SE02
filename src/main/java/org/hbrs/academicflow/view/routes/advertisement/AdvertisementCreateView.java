package org.hbrs.academicflow.view.routes.advertisement;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import java.time.Instant;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.hbrs.academicflow.control.advertisement.AdvertisementService;
import org.hbrs.academicflow.control.company.profile.CompanyProfileService;
import org.hbrs.academicflow.control.company.user.CompanyService;
import org.hbrs.academicflow.model.advertisement.Advertisement;
import org.hbrs.academicflow.model.company.profile.CompanyProfile;
import org.hbrs.academicflow.model.company.user.Company;
import org.hbrs.academicflow.model.location.Location;
import org.hbrs.academicflow.model.user.UserDTO;
import org.hbrs.academicflow.util.Constants;
import org.hbrs.academicflow.util.SessionAttributes;
import org.hbrs.academicflow.view.common.components.VerticalSpacerGenerator;
import org.hbrs.academicflow.view.common.layouts.AppView;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Route(value = Constants.Pages.ORGANIZATION_CREATE_AD, layout = AppView.class)
@PageTitle("Jobanzeige Erstellen")
@UIScope
@CssImport("./styles/views/backend/show-users-view.css")
public class AdvertisementCreateView extends AdvertisementDetailsOrg {

  private final transient AdvertisementService advertisementService;
  private final transient CompanyService companyService;
  private final transient CompanyProfileService profileService;
  private final Button saveButton = new Button("Jobanzeige veröffentlichen");
  private final Button resetButton = new Button("Leeren");
  private transient UserDTO current;
  private transient CompanyProfile profile;

  @PostConstruct
  void init() {
    this.add(new VerticalSpacerGenerator("1em").buildVerticalSpacer());
    addClassName("show-users-view");
    this.image.addClassName("profile-picture");
    add(this.image);
    // Start: Get current user
    this.current = SessionAttributes.getCurrentUser();
    if (this.current == null) {
      UI.getCurrent().navigate(Constants.Pages.LOGIN_VIEW);
      return;
    }
    // End: Get current user
    // Start: Load CompanyProfile
    this.profile = this.profileService.findCompanyProfileByUser(this.current);
    if (this.profile == null) {
      Notification.show(
          "Bitte erstellen sie zuerst ihr Unternehmensprofil",
          20000,
          Notification.Position.TOP_STRETCH);
    }
    // End: Load CompanyProfile
    // Start: Fill Components with CompanyProfile information
    this.fillFieldsWithProfileInformation();
    // End: Fill Components with CompanyProfile information
    this.resetButton.addClickListener(event -> this.fillFieldsWithProfileInformation());
    this.saveButton.addClickListener(event -> {
      if (this.titleField.getValue().equals("") || this.descriptionField.getValue().equals("")) {
        Notification notification = Notification.show("Bitte füllen Sie alle Felder aus", 8000,
            Notification.Position.TOP_STRETCH);
        notification.addThemeVariants(NotificationVariant.LUMO_PRIMARY);
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        return;
      }
      this.saveAndExit();
    });
    // Start: Build components
    final FormLayout formLayout = new FormLayout();
    jobTypeField.setLabel("Stellen-Typ");
    jobTypeField.setItems(
        "Vollzeit", "Teilzeit", "Praktikum", "Werksstudent", "Doktorand", "Master", "Andere");
    formLayout.add(
        this.titleField,
        this.jobTypeField,
        this.descriptionField,
        this.isRemoteCheckBox,
        this.saveButton,
        this.resetButton);
    formLayout.setColspan(this.saveButton, 1);
    formLayout.setColspan(this.resetButton, 1);
    this.add(formLayout);
    // End: Build components
  }

  private void fillFieldsWithProfileInformation() {
    this.titleField.setValue("");
    this.jobTypeField.setValue("");
    this.descriptionField.setValue("");
    this.isRemoteCheckBox.setValue(false);
  }

  private void saveAndExit() {
    final Company company = this.companyService.findCompanyByUser(this.current);
    if (company == null) {
      Notification.show(
          "Unerwarteter fehler, zu ihrem Nutzerkonto ist kein Unternehmen zugeordnet");
      return;
    }
    final Location location = this.profile.getLocation();
    final Advertisement advertisement =
        Advertisement.builder()
            .company(company)
            .location(location)
            .title(this.titleField.getValue())
            .description(this.descriptionField.getValue())
            .jobType(this.jobTypeField.getValue())
            .remote(this.isRemoteCheckBox.getValue())
            .active(true)
            .deadline(Instant.now().plusSeconds(30000))
            .build();
    this.advertisementService.doCreateAdvertisement(advertisement);
    Notification.show("Das Inserat wurde erstellt");
  }
}