package org.hbrs.academicflow.view.routes.advertisement;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import java.time.Instant;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hbrs.academicflow.control.advertisement.AdvertisementService;
import org.hbrs.academicflow.control.company.profile.CompanyProfileService;
import org.hbrs.academicflow.control.company.user.CompanyService;
import org.hbrs.academicflow.model.advertisement.Advertisement;
import org.hbrs.academicflow.model.company.user.Company;
import org.hbrs.academicflow.model.user.UserDTO;
import org.hbrs.academicflow.util.Constants;
import org.hbrs.academicflow.util.Constants.Pages;
import org.hbrs.academicflow.util.SessionAttributes;
import org.hbrs.academicflow.view.common.components.VerticalSpacerGenerator;
import org.hbrs.academicflow.view.common.layouts.AppView;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Route(value = Pages.ORGANIZATION_EDIT_AD, layout = AppView.class)
@PageTitle("Jobanzeige Erstellen")
@UIScope
@Slf4j
@CssImport("./styles/views/backend/show-users-view.css")
public class AdvertisementEditView extends AdvertisementDetailsOrg
    implements HasUrlParameter<String> {

  private final transient AdvertisementService advertisementService;
  private final transient CompanyService companyService;
  private final transient CompanyProfileService profileService;
  private final Button saveButton = new Button("Jobanzeige aktualisieren");
  private final Button resetButton = new Button("Zurücksetzen");
  transient Advertisement advertisement = null;
  private transient UserDTO current;

  void init() {
    this.add(new VerticalSpacerGenerator("1em").buildVerticalSpacer());
    addClassName("show-users-view");
    this.image.addClassName("profile-picture");
    add(this.image);
    this.resetButton.addClickListener(event -> this.fillFieldsWithProfileInformation());
    this.saveButton.addClickListener(event -> this.saveAndExit());
    this.saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    // Start: Build components
    final FormLayout formLayout = new FormLayout();
    formLayout.setResponsiveSteps(new ResponsiveStep("0", 1), new ResponsiveStep("800px", 6));
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
    formLayout.setColspan(this.saveButton, 2);
    formLayout.setColspan(this.resetButton, 2);
    formLayout.setColspan(this.titleField, 3);
    formLayout.setColspan(this.jobTypeField, 3);
    formLayout.setColspan(this.descriptionField, 6);
    formLayout.setColspan(this.isRemoteCheckBox, 6);
    Button deleteButton =
        new Button(
            "Löschen",
            click -> {
              final Dialog dialog = new Dialog();
              final VerticalLayout dialogLayout = buildDeleteConfirmDialog(dialog);
              dialog.add(dialogLayout);
              dialog.setModal(false);
              dialog.setDraggable(true);
              add(dialog);
              dialog.open();
            });
    deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
    formLayout.add(deleteButton);
    formLayout.setColspan(deleteButton, 2);

    // Start: Fill Components with CompanyProfile information
    this.fillFieldsWithProfileInformation();
    // End: Fill Components with CompanyProfile information
    this.add(formLayout);
  }

  public VerticalLayout buildDeleteConfirmDialog(Dialog dialog) {
    // FROM DOCS: https://vaadin.com/docs/v14/ds/components/dialog
    final H2 headline = new H2("Jobanzeige löschen");
    headline.getStyle().set("margin", "0").set("font-size", "1.5em").set("font-weight", "bold");
    final HorizontalLayout header = new HorizontalLayout(headline);
    header.getElement().getClassList().add("draggable");
    header.setSpacing(false);
    header
        .getStyle()
        .set("border-bottom", "1px solid var(--lumo-contrast-20pct)")
        .set("cursor", "move");
    // Use negative margins to make draggable header stretch over full width,
    // covering the padding of the dialog
    header
        .getStyle()
        .set("padding", "var(--lumo-space-m) var(--lumo-space-l)")
        .set("margin", "calc(var(--lumo-space-s) * -1) calc(var(--lumo-space-l) * -1) 0");
    final Button cancelButton = new Button("Abbrechen", e -> dialog.close());
    final Button deactivateButton =
        new Button(
            "Deaktivieren",
            e -> {
              // delete ad by deactivating it
              advertisement.setActive(false);
              advertisementService.updateAdvertisement(advertisement);
              log.info("Deleted (deactivated) advertisement with id: {}", advertisement.getId());
              dialog.close();
            });
    deactivateButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    final HorizontalLayout buttonLayout = new HorizontalLayout(cancelButton, deactivateButton);
    buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
    final VerticalLayout dialogLayout = new VerticalLayout(header, buttonLayout);
    dialogLayout.setPadding(false);
    dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
    dialogLayout.getStyle().set("width", "300px").set("max-width", "100%");
    return dialogLayout;
  }

  private void fillFieldsWithProfileInformation() {
    if (this.profileService.findCompanyProfileByUser(this.current) == null) {
      Notification.show(
          "Bitte erstellen sie zuerst ihr Unternehmensprofil",
          20000,
          Notification.Position.TOP_STRETCH);
    }
    // End: Load CompanyProfile
    this.titleField.setValue(advertisement.getTitle());
    this.jobTypeField.setValue(advertisement.getJobType());
    this.descriptionField.setValue(advertisement.getDescription());
    this.isRemoteCheckBox.setValue(advertisement.getRemote());
  }

  private void saveAndExit() {
    this.advertisement.setTitle(this.titleField.getValue());
    this.advertisement.setDescription(this.descriptionField.getValue());
    this.advertisement.setJobType(this.jobTypeField.getValue());
    this.advertisement.setRemote(this.isRemoteCheckBox.getValue());
    this.advertisement.setDeadline(Instant.now().plusSeconds(30000));
    this.advertisement.setActive(true);
    // Über die ID angesteuerte Anzeigen können hier wieder aktiviert werden.
    this.advertisementService.updateAdvertisement(advertisement);
    Notification.show("Die Stellenanzeige wurde aktualisiert.");
  }

  @Override
  public void setParameter(BeforeEvent event, String parameter) {
    if (parameter == null) {
      UI.getCurrent().navigate(Pages.ORGANIZATION_LIST_ADS);
    } else {
      // Start: Get current user
      this.current = SessionAttributes.getCurrentUser();
      if (this.current == null) {
        UI.getCurrent().navigate(Constants.Pages.LOGIN_VIEW);
        return;
      }
      // End: Get current user
      this.advertisement =
          this.advertisementService.findFullAdvertisementById(UUID.fromString(parameter));
      final Company company = this.companyService.findCompanyByUser(this.current);
      if (company == null) {
        Notification.show(
            "Unerwarteter fehler, zu ihrem Nutzerkonto ist kein Unternehmen zugeordnet");
        return;
      }
      if (!this.advertisement.getCompany().getId().equals(company.getId())) {
        log.info(
            "Company {} tried to edit advertisement {} of company {}",
            this.current.getUsername(),
            this.advertisement.getId(),
            this.advertisement.getCompany().getName());
        UI.getCurrent().navigate(Pages.ORGANIZATION_LIST_ADS);
        Notification.show(
            "Sie sind nicht berechtigt, diese Anzeige zu bearbeiten",
            20000,
            Notification.Position.TOP_STRETCH);
        return;
      }
      log.info(
          "Company {} wants to edit advertisement {} of company {} which they are allowed to",
          this.current.getUsername(),
          this.advertisement.getId(),
          this.advertisement.getCompany().getName());
      this.init();
    }
  }
}