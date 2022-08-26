package org.hbrs.academicflow.view.routes.profile;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.time.Instant;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.hbrs.academicflow.control.company.profile.CompanyProfileService;
import org.hbrs.academicflow.control.rating.RatingService;
import org.hbrs.academicflow.control.student.StudentService;
import org.hbrs.academicflow.model.company.profile.CompanyProfile;
import org.hbrs.academicflow.model.rating.Rating;
import org.hbrs.academicflow.util.Constants;
import org.hbrs.academicflow.util.SessionAttributes;
import org.hbrs.academicflow.view.common.components.RatingGenerator;
import org.hbrs.academicflow.view.common.layouts.AppView;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@PageTitle("Unternehmensprofil")
@Route(value = Constants.Pages.ORGANIZATION_PROFILE_STUDENT_VIEW, layout = AppView.class)
@CssImport("./styles/views/main/main-view.css")
public class OrganisationProfile extends Div implements HasUrlParameter<String> {

  private final transient CompanyProfileService service;
  private final transient StudentService studentService;
  private final transient RatingService ratingService;
  private final Label city = new Label();
  private final H3 name = new H3();
  private final Label zipCode = new Label();
  private final Label country = new Label();
  private final Label street = new Label();
  private final Label description = new Label();
  private final Label houseNumber = new Label();
  private final Image image = new Image("images/corporation-profile.png", "Unternehmensbild");
  private final Image companyImage =
      new Image("images/corporation-profile.png", "Unternehmensbild");
  private transient CompanyProfile profile;

  void init() {
    addClassName("show-users-view");
    if (profile == null) {
      Notification.show("Company nicht gefunden");
    } else {
      fillCompanyInfoIntoFields();
      HorizontalLayout topLayout = new HorizontalLayout();
      VerticalLayout topRightLayout = new VerticalLayout();
      VerticalLayout layout = new VerticalLayout();
      Component rating = new RatingGenerator().getRatingDisplay(profile.getRating());
      /*

       */
      this.image.addClassName("profile-picture");
      this.companyImage.addClassName("companyPic");
      topLayout.setAlignItems(FlexComponent.Alignment.CENTER);
      topLayout.setJustifyContentMode(JustifyContentMode.CENTER);
      topLayout.add(companyImage);
      topLayout.add(topRightLayout);
      topRightLayout.setWidth(null);
      topRightLayout.add(name);
      topRightLayout.add(rating);
      layout.setAlignItems(FlexComponent.Alignment.CENTER);
      layout.setWidth("100%");
      layout.add(street, houseNumber, zipCode, city, country, description);
      add(topLayout, layout);
      Button submitRatingButton = new Button("Bewertung absenden");
      Button openRatingAreaButton = new Button("Jetzt Bewertung verfassen");
      VerticalLayout ratingLayout = new VerticalLayout();
      ratingLayout.setAlignItems(Alignment.CENTER);
      ratingLayout.setVisible(false);
      openRatingAreaButton.addClickListener(
          event -> ratingLayout.setVisible(!ratingLayout.isVisible()));
      boolean isStudent = SessionAttributes.isCurrentUserStudent(studentService);
      RatingGenerator rg = new RatingGenerator();
      ratingLayout.add(rg.getRatingButton());
      ratingLayout.add(new TextArea("Bewertung"));
      ratingLayout.add(submitRatingButton);
      if (isStudent) {
        layout.add(openRatingAreaButton);
        submitRatingButton.addClickListener(
            event -> {
              Rating r = new Rating();
              r.setCompanyProfile(profile);
              r.setStudent(
                  studentService.findStudentByUserID(SessionAttributes.getCurrentUser().getId()));
              r.setValue(rg.getCurrentRatingIndex() + 1);
              r.setTimestamp(Instant.now());
              ratingService.doCreateRating(r);
              Notification.show("Bewertung abgesendet");
              ratingLayout.setVisible(false);
            });
        layout.add(ratingLayout);
      }
    }
  }

  private void fillCompanyInfoIntoFields() {
    this.name.add(this.profile.getCompany().getName());
    this.street.add("Strasse:\n" + this.profile.getLocation().getStreet());
    this.houseNumber.add("Hausnummer:\n" + this.profile.getLocation().getHouseNumber());
    this.zipCode.add("PLZ:\n" + this.profile.getLocation().getZipCode());
    this.city.add("Stadt:\n" + this.profile.getLocation().getCity());
    this.country.add("Land:\n" + this.profile.getLocation().getCountry());
    this.description.add("Beschreibung:\n" + this.profile.getDescription());
  }

  @Override
  public void setParameter(BeforeEvent event, String parameter) {
    if (parameter == null) {
      return;
    } else {
      this.profile = service.findCompanyByCompanyId(UUID.fromString(parameter));
      if (this.profile == null) {
        Notification.show("Company nicht gefunden");
        UI.getCurrent().navigate(Constants.Pages.WELCOME_VIEW);
      }
    }
    this.init();
  }
}
