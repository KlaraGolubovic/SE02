package org.hbrs.academicflow.view.routes.backend.component;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.function.Consumer;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hbrs.academicflow.control.advertisement.AdvertisementService;
import org.hbrs.academicflow.control.apply.ApplyService;
import org.hbrs.academicflow.control.company.profile.CompanyProfileService;
import org.hbrs.academicflow.control.company.user.CompanyService;
import org.hbrs.academicflow.control.exception.DatabaseUserException;
import org.hbrs.academicflow.control.location.LocationService;
import org.hbrs.academicflow.control.permission.PermissionGroupService;
import org.hbrs.academicflow.control.rating.RatingService;
import org.hbrs.academicflow.control.student.StudentService;
import org.hbrs.academicflow.control.student.profile.StudentProfileService;
import org.hbrs.academicflow.control.user.UserService;
import org.hbrs.academicflow.model.advertisement.Advertisement;
import org.hbrs.academicflow.model.apply.Apply;
import org.hbrs.academicflow.model.company.profile.CompanyProfile;
import org.hbrs.academicflow.model.company.user.Company;
import org.hbrs.academicflow.model.location.Location;
import org.hbrs.academicflow.model.permission.PermissionGroup;
import org.hbrs.academicflow.model.rating.Rating;
import org.hbrs.academicflow.model.student.user.Student;
import org.hbrs.academicflow.model.user.User;
import org.hbrs.academicflow.util.Encryption;
import org.hbrs.academicflow.view.common.components.VerticalSpacerGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Scope(value = "prototype")
public class DemoDummyDataCreator extends Div {

  public static final String LAURA_STUDENT = "LauraStudent";
  public static final String PAUL_STUDENT = "PaulStudent";
  public static final String HENRY_STUDENT = "HenryStudent";
  public static final String ERIN_STUDENT = "ErinStudent";
  public static final String MAX_STUDENT = "MaxStudent";
  public static final String STUDENT = "Student";
  public static final String ORGA_PAUL = "OrgaPaul";
  public static final String ORGA_LAURA = "OrgaLaura";
  public static final String ORGA_HENRY = "OrgaHenry";
  public static final String ORGA_ERIN = "OrgaErin";
  public static final String ORGA_MAX = "OrgaMax";
  public static final String PAULGMBH_DE = "Orga@paulgmbh.de";
  public static final String STUDYHAPPY_DE = "@studyhappy.de";
  private static final String ERROR_WHILE_PURGING = "Error while purging ";
  private final transient PermissionGroupService permissionService;
  private final transient StudentService studentService;
  private final transient CompanyService companyService;
  private final transient UserService userService;
  private final transient AdvertisementService advertisementService;
  private final transient ApplyService applyService;
  private final transient StudentProfileService studentProfileService;
  private final transient CompanyProfileService companyProfileService;
  private final transient LocationService locationService;
  private final transient RatingService ratingService;
  private final TextField confirmationTextField = new TextField("Enter ENABLEDEMO to confirm");
  private final Button resetDemoStateButton = new Button("Enable Demo State (Reset)");
  private int del = 0;
  private int created = 0;
  private transient User companyuser = null;
  private transient User studentuser = null;
  private transient Advertisement applyStored;

  @PostConstruct
  private void doPostConstruct() {
    addClassName("show-users-view");
    this.add(this.doCreatePermissionGroupSection());
    this.resetDemoStateButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
    this.resetDemoStateButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    this.confirmationTextField.getElement().getStyle().set("margin-left", "auto");
    this.resetDemoStateButton.getElement().getStyle().set("margin-right", "auto");
    this.add(new VerticalSpacerGenerator("3vh").buildVerticalSpacer());
  }

  private Component doCreatePermissionGroupSection() {
    Div div = new Div();
    div.add(new H3("DEMOSTATE AREA"));

    Collection<User> studentArray = new ArrayList<>();

    Collection<User> companyArray = new ArrayList<>();
    this.resetDemoStateButton.addClickListener(event -> {
      if (!this.confirmationTextField.getValue().equals("ENABLEDEMO")) {
        Notification.show("Nothing happened, wrong input.", 3000, Notification.Position.MIDDLE);
        return;
      }
      Notification.show("Reverting back to demo state, please wait!", 3000,
          Notification.Position.MIDDLE);
      this.purge();
      Notification.show("Deleted " + this.del + " Entities!", 3000, Notification.Position.MIDDLE);
      this.setPermissionGroups();

      this.setListValues(studentArray, companyArray);
      // order is important setusers generates the values for setstudents
      Consumer<User> setComps = this::setCompanies;
      companyArray.forEach(setComps.andThen(this::setAdvertisements));
      Consumer<User> setStuds = this::setStudents;
      studentArray.forEach(setStuds.andThen(this::setRatings).andThen(this::setApplications));
      Notification.show("Standard restored.", 3000, Notification.Position.MIDDLE);

      Notification.show("Created " + this.created + " Entities!", 3000,
          Notification.Position.MIDDLE);
      log.info("Demostate finished, " + this.created + " entries created\n");
    });
    final FormLayout formLayout = new FormLayout();
    formLayout.add(this.confirmationTextField, this.resetDemoStateButton);
    formLayout.setColspan(this.confirmationTextField, 1);
    formLayout.setColspan(this.resetDemoStateButton, 1);
    div.add(formLayout);
    return div;
  }

  private void setAdvertisements(User user) {
    log.info("Setting advertisements for " + user.getUsername());
    final Location location = this.locationService.save(
        Location.builder().city("Bonn").country("Deutschland").zipCode("53117").houseNumber("5B")
            .street("Friedensstrasse").build());
    Company comp = companyService.findCompanyByFullUser(this.companyuser);
    if (comp == null) {
      log.error("Company not found");
      return;
    }
    this.applyStored = this.advertisementService.doCreateAdvertisement(
        Advertisement.builder().active(true).company(comp).deadline(Instant.now()).description(
                "Bei Exxeta fordern wir das traditionelle Konzept von Beratung und Tech heraus."
                    + " Über 1000 Kolleg:innen an verschiedenen Standorten schaffen jeden Tag"
                    + " gemeinsam digitale Lösungen, verändern Märkte und Mindsets – angetrieben"
                    + " von unserer Leidenschaft für Technologie, unserem Teamspirit und dem Drang,"
                    + " echten Impact zu schaffen. Hightech with a heartbeat eben. \n" + "\n"
                    + "Du bist interessiert an Softwareentwicklung und hast bereits erste"
                    + " Coding-Erfahrung? Du weißt noch nicht genau, welche Rolle in der"
                    + " Entwicklung am besten zu dir passt? Dann komm zu Exxeta und wir finden es"
                    + " gemeinsam heraus. Gestalte dein Studium zusammen mit uns! Bringe dich mit"
                    + " neuen Ideen und Themen sowie deinen Stärken ein. Wir bieten keine"
                    + " festgeschriebene Position, sondern die Möglichkeit deine Rolle im"
                    + " Unternehmen selbst zu finden oder eine neue Rolle zu schaffen. Wähle"
                    + " selbst, ob du im Frontend oder Backend tätig sein willst.")
            .jobType("Werkstudent").remote(true).location(location)
            .title("Werkstudent/Praktikant Softwareentwicklung (m/w/d)").build());
    this.created = this.created + 2;
  }

  private void setRatings(User u) {
    Rating a = new Rating();
    log.info("Setting ratings for " + u.getUsername());
    a.setStudent(studentService.findStudentByUserID(this.studentuser.getId()));
    a.setTimestamp(Instant.now());
    a.setValue(5);
    a.setCompanyProfile(this.companyProfileService.findCompanyProfileByUser(this.companyuser));
    this.ratingService.doCreateRating(a);
    this.created++;
  }

  private void setApplications(User u) {
    Apply a = new Apply();
    log.info("Setting applications for " + u.getUsername());
    Student student = studentService.findStudentByUserID(this.studentuser.getId());
    if (student == null) {
      log.error("Student not found");
      return;
    }
    a.setStudent(student);
    a.setApplied(Instant.now());
    a.setNote(
        "Guten Tag, mein Name ist " + student.getFirstName() + " und ich studiere an der HBRS. "
            + "Ich bin sehr interessiert an"
            + " Softwareentwicklung und bin bereits Erfahren. Ich weiß noch nicht"
            + " genau, welche Rolle in der Entwicklung am besten zu mir passt. Aber ich würde gerne zu einem Bewerbungsgespräch vorbeikommen. "
            + "Mit freundlichen Grüßen," + " " + student.getFirstName() + " "
            + student.getLastName());
    a.setAdvertisement(applyStored);
    this.applyService.doSaveApplication(a);
    this.created++;
  }

  private void setListValues(Collection<User> studentArray, Collection<User> companyArray) {

    studentArray.add(
        User.builder().username(LAURA_STUDENT).password(Encryption.sha256(LAURA_STUDENT))
            .email(LAURA_STUDENT + STUDYHAPPY_DE).build());
    studentArray.add(User.builder().username(PAUL_STUDENT).password(Encryption.sha256(PAUL_STUDENT))
        .email(PAUL_STUDENT + STUDYHAPPY_DE).build());
    studentArray.add(
        User.builder().username(HENRY_STUDENT).password(Encryption.sha256(HENRY_STUDENT))
            .email(HENRY_STUDENT + STUDYHAPPY_DE).build());
    studentArray.add(User.builder().username(ERIN_STUDENT).password(Encryption.sha256(ERIN_STUDENT))
        .email(ERIN_STUDENT + STUDYHAPPY_DE).build());
    studentArray.add(User.builder().username(MAX_STUDENT).password(Encryption.sha256(MAX_STUDENT))
        .email(MAX_STUDENT + STUDYHAPPY_DE).build());

    companyArray.add(User.builder().username(ORGA_LAURA).password(Encryption.sha256(ORGA_LAURA))
        .email("umaidwal@bado.de").build());
    companyArray.add(User.builder().username(ORGA_HENRY).password(Encryption.sha256(ORGA_HENRY))
        .email("umadsail@bado.de").build());
    companyArray.add(User.builder().username(ORGA_ERIN).password(Encryption.sha256(ORGA_ERIN))
        .email("umadsadsdil@bado.de").build());
    companyArray.add(User.builder().username(ORGA_MAX).password(Encryption.sha256(ORGA_MAX))
        .email("umadsadasil@bado.de").build());
    // Orga_Paul als Letztes hinzufügen, damit alle bewerbungen bei orgapaul eingehen
    companyArray.add(
        User.builder().username(ORGA_PAUL).password(Encryption.sha256(ORGA_PAUL)).email(PAULGMBH_DE)
            .build());
  }

  private void purge() {
    // remove everything
    // apply -> advertisement -> organizations -> user -> permissiongroup
    log.info("Purging ratings");
    try {
      this.ratingService.findAll().forEach(rating -> {
        this.ratingService.delete(rating);
        this.del++;
      });
    } catch (Exception e) {
      Notification.show(ERROR_WHILE_PURGING + "ratings", 3000, Notification.Position.MIDDLE);
      log.error(ERROR_WHILE_PURGING + "rating: " + e.getMessage());
    }
    // ----------------------------------------------------------------------------------------------------------------------
    log.info("Purging applications");
    try {
      this.applyService.findAll().forEach(apply -> {
        this.applyService.delete(apply);
        this.del++;
      });
    } catch (Exception e) {
      Notification.show(ERROR_WHILE_PURGING + "applications", 3000, Notification.Position.MIDDLE);
      log.error(ERROR_WHILE_PURGING + "applications: " + e.getMessage());
    }
    // ----------------------------------------------------------------------------------------------------------------------
    log.info("Purging advertisements");
    try {
      this.advertisementService.findAll().forEach(advertisement -> {
        this.advertisementService.deleteAdvertisementById(advertisement.getId());
        this.del++;
      });
    } catch (Exception e) {
      Notification.show(ERROR_WHILE_PURGING + "advertisements", 3000, Notification.Position.MIDDLE);
      log.error(ERROR_WHILE_PURGING + "advertisements: " + e.getMessage());
    }
    // ----------------------------------------------------------------------------------------------------------------------
    log.info("Purging companyProfiles");
    try {
      this.companyProfileService.findAll().forEach(companyProfile -> {
        this.companyProfileService.deleteCompanyProfile(companyProfile);
        this.del++;
      });
    } catch (Exception e) {
      Notification.show(ERROR_WHILE_PURGING + "companyProfiles", 3000,
          Notification.Position.MIDDLE);
      log.error(ERROR_WHILE_PURGING + "companyProfile: " + e.getMessage());
    }
    // ----------------------------------------------------------------------------------------------------------------------
    log.info("Purging companies");
    try {
      this.companyService.findAll().forEach(company -> {
        this.companyService.deleteCompany(company);
        this.del++;
      });
    } catch (Exception e) {
      Notification.show(ERROR_WHILE_PURGING + "companies", 3000, Notification.Position.MIDDLE);
      log.error(ERROR_WHILE_PURGING + "company: " + e.getMessage());
    }
    // ----------------------------------------------------------------------------------------------------------------------
    log.info("Purging studentProfiles");
    try {
      this.studentProfileService.findAll().forEach(studentProfile -> {
        this.studentProfileService.deleteStudentProfile(studentProfile);
        this.del++;
      });
    } catch (Exception e) {
      Notification.show(ERROR_WHILE_PURGING + "studentProfiles", 3000,
          Notification.Position.MIDDLE);
      log.error(ERROR_WHILE_PURGING + "studentProfile: " + e.getMessage());
    }
    // ----------------------------------------------------------------------------------------------------------------------
    log.info("Purging students");
    try {
      this.studentService.findAll().forEach(student -> {
        this.studentService.deleteStudent(student);
        this.del++;
      });
    } catch (Exception e) {
      Notification.show(ERROR_WHILE_PURGING + "student", 3000, Notification.Position.MIDDLE);
      log.error(ERROR_WHILE_PURGING + "student: " + e.getMessage());
    }
    // ----------------------------------------------------------------------------------------------------------------------
    log.info("Purging locations");
    try {
      this.locationService.findAll().forEach(location -> {
        this.locationService.deleteById(location.getId());
        this.del++;
      });
    } catch (Exception e) {
      Notification.show(ERROR_WHILE_PURGING + "locations", 3000, Notification.Position.MIDDLE);
      log.error(ERROR_WHILE_PURGING + "location: " + e.getMessage());
    }
    // ----------------------------------------------------------------------------------------------------------------------
    log.info("Purging users");
    try {
      this.userService.findAllUsers().forEach(user -> {
        this.userService.deleteById(user.getId());
        this.del++;
      });
    } catch (Exception e) {
      Notification.show(ERROR_WHILE_PURGING + "users", 3000, Notification.Position.MIDDLE);
      log.error(ERROR_WHILE_PURGING + "user: " + e.getMessage());
    }
    // ----------------------------------------------------------------------------------------------------------------------
    log.info("Purging permissionGroups");
    try {
      this.permissionService.findAllUncached().forEach(group -> {
        log.info("deleting " + group.getName());
        this.permissionService.deleteById(group.getId());
        this.del++;
      });
    } catch (Exception e) {
      Notification.show(ERROR_WHILE_PURGING + "permissionGroups", 3000,
          Notification.Position.MIDDLE);
      log.error(ERROR_WHILE_PURGING + "permissionGroup: " + e.getMessage());
    }
    log.info("Purge finished, " + this.del + " entries deleted\n");
  }

  private void setPermissionGroups() {
    log.info("Creating permission groups");
    try {
      this.permissionService.save(new PermissionGroup("Organisation", 5, new HashSet<>()));
      // GGF error bei Orga exists aber Student nicht
      this.permissionService.save(new PermissionGroup(STUDENT, 5, new HashSet<>()));
      this.created = this.created + 2;
    } catch (Exception e) {
      Notification.show("Error while creating permission groups", 3000,
          Notification.Position.MIDDLE);
      log.error("Error while creating permission groups: " + e.getMessage());
    }
  }

  private void setCompanies(User company) {

    company.addPermissionGroup(this.permissionService.findPermissionGroupByName("Organisation"));
    try {
      this.companyuser = this.userService.createUser(company);
      this.created++;
    } catch (DatabaseUserException e) {
      Notification.show("User could not be created");
    }
    this.companyService.doCreateCompany(
        Company.builder().name(company.getUsername() + " GmbH").phone("01642388468")
            .user(this.companyuser).build());
    final Location location = this.locationService.save(
        Location.builder().city("Bonn").country("Deutschland").zipCode("53117").houseNumber("6B")
            .street("Friedensstrasse").build());
    Company cu = this.companyService.findCompanyByFullUser(this.companyuser);
    if (cu == null) {
      log.error("Company not found");
      return;
    }
    this.companyProfileService.updateCompanyProfile(
        CompanyProfile.builder().company(cu).description("GmbH ist eine Firma").location(location)
            .image(2).build());
    this.created = this.created + 2;
  }

  private void setStudents(User student) {
    student.addPermissionGroup(this.permissionService.findPermissionGroupByName(STUDENT));
    try {
      this.studentuser = this.userService.createUser(student);
      this.created++;
    } catch (DatabaseUserException e) {
      Notification.show("User could not be created: " + e.getMessage());
    }
    this.studentService.doCreateStudent(
        Student.builder().firstName(StringUtils.removeEnd(student.getUsername(), STUDENT))
            .lastName(STUDENT).phone("46912874").user(this.studentuser).build());
    this.created++;
  }


}
