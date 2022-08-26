package org.hbrs.academicflow.view.routes.profile.component;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hbrs.academicflow.control.company.profile.CompanyProfileService;
import org.hbrs.academicflow.control.company.user.CompanyService;
import org.hbrs.academicflow.control.location.LocationMapper;
import org.hbrs.academicflow.control.location.LocationService;
import org.hbrs.academicflow.model.company.profile.CompanyProfile;
import org.hbrs.academicflow.model.company.user.Company;
import org.hbrs.academicflow.model.location.Location;
import org.hbrs.academicflow.model.location.LocationDTO;
import org.hbrs.academicflow.model.user.UserDTO;
import org.hbrs.academicflow.util.Constants;
import org.hbrs.academicflow.util.SessionAttributes;
import org.hbrs.academicflow.view.common.components.VerticalSpacerGenerator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@Slf4j
@UIScope
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class EditOrganisationProfile extends Div {

  private final transient LocationMapper locationMapper;
  private final transient CompanyService companyService;
  private final transient LocationService locationService;
  private final transient CompanyProfileService profileService;
  private final Button saveButton = new Button("Profil speichern");
  private final Button resetButton = new Button("Zurücksetzen");
  private final TextField cityField = new TextField("Stadt");
  private final TextField nameField = new TextField("Name");
  private final TextField zipCodeField = new TextField("PLZ");
  private final TextField countryField = new TextField("Land");
  private final TextField streetField = new TextField("Straße");
  private final TextArea descriptionField = new TextArea("Beschreibung");
  private final TextField houseNumberField = new TextField("Hausnummer");
  private final Image image = new Image("images/corporation-profile.png", "Unternehmensbild");
  private final AtomicBoolean newlyGenerated = new AtomicBoolean();
  private transient UserDTO loginUser;
  private transient CompanyProfile profile;

  @PostConstruct
  void init() {
    this.add(new VerticalSpacerGenerator("1em").buildVerticalSpacer());
    addClassName("show-users-view");
    this.image.addClassName("profile-picture");
    add(this.image);
    this.loginUser = SessionAttributes.getCurrentUser();
    if (this.loginUser == null) {
      UI.getCurrent().navigate(Constants.Pages.LOGIN_VIEW);
      return;
    }
    this.profile = this.profileService.findCompanyProfileByUser(this.loginUser);
    if (this.profile == null) {
      this.newlyGenerated.set(true);
      this.profile = this.buildEmptyCompanyProfile();
    }
    this.fillFieldsWithProfileInformation();
    this.resetButton.addClickListener(event -> this.fillFieldsWithProfileInformation());
    this.saveButton.addClickListener(event -> this.saveAndExit());
    // Start: Build components
    final FormLayout formLayout = new FormLayout();
    formLayout.add(
        this.nameField,
        this.streetField,
        this.houseNumberField,
        this.zipCodeField,
        this.cityField,
        this.countryField,
        this.descriptionField,
        this.saveButton,
        this.resetButton);
    formLayout.setResponsiveSteps(new ResponsiveStep("0", 1), new ResponsiveStep("800px", 8));
    formLayout.setColspan(this.nameField, 8);
    // --------------------------------------------------------------------------------------------
    formLayout.setColspan(this.streetField, 7);
    formLayout.setColspan(this.houseNumberField, 1);
    // --------------------------------------------------------------------------------------------
    formLayout.setColspan(this.zipCodeField, 2);
    formLayout.setColspan(this.cityField, 3);
    formLayout.setColspan(this.countryField, 3);
    // --------------------------------------------------------------------------------------------
    formLayout.setColspan(this.descriptionField, 8);
    formLayout.setColspan(this.saveButton, 4);
    formLayout.setColspan(this.resetButton, 4);
    this.add(formLayout);
    // End: Build components
  }

  private void fillFieldsWithProfileInformation() {
    if (this.profile == null) {
      return;
    }
    this.descriptionField.setValue(this.profile.getDescription());
    this.nameField.setValue(this.profile.getCompany().getName());
    this.countryField.setValue(this.profile.getLocation().getCountry());
    this.cityField.setValue(this.profile.getLocation().getCity());
    this.houseNumberField.setValue(this.profile.getLocation().getHouseNumber());
    this.streetField.setValue(this.profile.getLocation().getStreet());
    this.zipCodeField.setValue(this.profile.getLocation().getZipCode());
  }

  private void saveAndExit() {
    final Company company = this.companyService.findCompanyByUser(this.loginUser);
    if (company == null) {
      Notification.show("Es ist ein Fehler aufgetreten. Bitte versuchen Sie es erneut.");
      return;
    }
    final LocationDTO locationDTO = this.toLocationDTO();
    company.setName(this.nameField.getValue());
    this.companyService.updateCompany(company);
    log.info("Saving company name: {}", company.getName());
    if (this.newlyGenerated.get()) {
      final Location location =
          this.locationService.save(this.locationMapper.toEntity(locationDTO));
      final CompanyProfile companyProfile =
          CompanyProfile.builder()
              .company(company)
              .description(this.descriptionField.getValue())
              .image(0)
              .location(location)
              .build();
      this.profileService.updateCompanyProfile(companyProfile);
      Notification.show("Das Profil wurde erstellt");
    } else {
      final Location location =
          this.locationMapper.update(
              locationDTO, this.profile.getLocation().getId(), this.profile.getLocation());
      this.profile.setDescription(this.descriptionField.getValue());
      this.profile.setLocation(this.locationService.save(location));
      this.profileService.updateCompanyProfile(this.profile);
      Notification.show("Das Profil wurde aktualisiert");
    }
  }

  private @NotNull LocationDTO toLocationDTO() {
    return new LocationDTO(
        this.streetField.getValue(),
        this.houseNumberField.getValue(),
        this.cityField.getValue(),
        this.zipCodeField.getValue(),
        this.countryField.getValue());
  }

  private @Nullable CompanyProfile buildEmptyCompanyProfile() {
    final Company company = this.companyService.findCompanyByUser(this.loginUser);
    if (company == null) {
      return null;
    }
    final Location location =
        Location.builder().zipCode("").street("").houseNumber("").country("").city("").build();
    return CompanyProfile.builder()
        .location(location)
        .company(company)
        .description("")
        .image(0)
        .build();
  }
}
