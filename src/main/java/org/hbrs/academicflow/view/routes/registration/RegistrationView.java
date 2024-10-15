package org.hbrs.academicflow.view.routes.registration;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.PropertyId;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hbrs.academicflow.control.company.user.CompanyService;
import org.hbrs.academicflow.control.exception.DatabaseUserException;
import org.hbrs.academicflow.control.permission.PermissionGroupService;
import org.hbrs.academicflow.control.student.StudentService;
import org.hbrs.academicflow.control.user.UserService;
import org.hbrs.academicflow.model.company.user.Company;
import org.hbrs.academicflow.model.permission.PermissionGroup;
import org.hbrs.academicflow.model.student.user.Student;
import org.hbrs.academicflow.model.user.User;
import org.hbrs.academicflow.util.Constants;
import org.hbrs.academicflow.util.Encryption;
import org.hbrs.academicflow.view.common.components.LabeledButtonCollection;
import org.hbrs.academicflow.view.common.layouts.PublicAppView;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Route(value = Constants.Pages.REGISTER_VIEW, layout = PublicAppView.class)
@PageTitle("Registrieren")
@CssImport("./styles/views/main/main-view.css")
@CssImport("./styles/views/main/buttons.css")
public class RegistrationView extends Div {

  private final Button saveButton = new Button("Registrieren");
  private final Button cancelButton =
      LabeledButtonCollection.loginButton("Bereits registriert? Hier anmelden!");
  private final TextField usernameField = new TextField("Benutzername");
  private final EmailField mailField = new EmailField("E-Mail");
  private final PasswordField passwordField = new PasswordField("Passwort");
  private final TextField firstNameField = new TextField("Vorname");
  private final TextField lastNameField = new TextField("Nachname");
  private final TextField phoneField = new TextField("Telefonnummer");

  @PropertyId("roles")
  private final Select<String> groupSelector = new Select<>("Rolle");

  private final transient UserService userService;
  private final transient StudentService studentService;
  private final transient PermissionGroupService groupService;
  private final transient CompanyService companyService;

  @PostConstruct
  public void doInitialSetup() {
    saveButton.setId("registerFormSubmit");
    usernameField.setId("usernameField");
    addClassName("registration-view");
    this.groupSelector.setItems("Organisation", "Student");
    this.groupSelector.setLabel("Student oder Unternehmen, bitte wählen:");
    this.groupSelector.setRequiredIndicatorVisible(true);
    this.doClearForm();
    this.cancelButton.addClickListener(event -> this.doClearForm());
    firstNameField.setEnabled(false);
    lastNameField.setEnabled(false);
    phoneField.setEnabled(false);
    usernameField.setRequired(true);
    // Start: build layout
    Div div = new Div();
    final VerticalLayout layout = new VerticalLayout();
    Span span = new Span(new H1("Registrieren"));
    layout.add(span);
    ComponentEventListener<ClickEvent<Button>> clbCompany = createCompanyEventListener();
    ComponentEventListener<ClickEvent<Button>> clbStudent = createStudentEventListener();
    this.groupSelector.addValueChangeListener(
        change -> {
          if ("Organisation".equals(groupSelector.getValue())) {
            // Dieser Gruppenname identifiziert Orgas!
            firstNameField.clear();
            lastNameField.clear();
            phoneField.clear();
            firstNameField.setEnabled(true);
            lastNameField.setEnabled(false);
            phoneField.setEnabled(true);
            firstNameField.setLabel("Name");
            this.saveButton.addClickListener(clbCompany);
          } else {
            // Assuming student
            firstNameField.clear();
            lastNameField.clear();
            phoneField.clear();
            firstNameField.setEnabled(true);
            lastNameField.setEnabled(true);
            phoneField.setEnabled(true);
            firstNameField.setLabel("Vorname");
            this.saveButton.addClickListener(clbStudent);
          }
        });
    layout.add(this.groupSelector);
    layout.add(this.doCreateFormLayout());
    div.add(layout);
    add(div);
  }

  private ComponentEventListener<ClickEvent<Button>> createStudentEventListener() {
    return event -> {
      try {
        final User user = createUser();
        if (user == null) {
          return;
        }
        final Student student = this.studentService.doCreateStudent(this.buildStudent(user));
        if (student == null) {
          Notification.show("Student could not be created");
          return;
        }
        Notification.show("User account has been created");
        this.doClearForm();
        UI ui = UI.getCurrent();
        ui.getPage().getHistory().pushState(null, "#");
        ui.navigate(Constants.Pages.LOGIN_VIEW);
      } catch (IllegalArgumentException e) {
        log.error(e.getMessage());
        Notification.show(e.getMessage());
      }
    };
  }

  private ComponentEventListener<ClickEvent<Button>> createCompanyEventListener() {
    return event -> {
      try {
        final User user = createUser();
        if (user == null) {
          return;
        }
        final Company comp = this.companyService.doCreateCompany(this.buildCompany(user));
        if (comp == null) {
          Notification.show("Company could not be created");
          return;
        }
        Notification.show("User account has been created");
        this.doClearForm();
      } catch (IllegalArgumentException e) {
        log.error(e.getMessage());
        Notification.show(e.getMessage());
      }
    };
  }

  /**
   * Handles the creation of the user in the DB uses the buildUser Method
   *
   * @return ``null`` on failure or passing on from createUser
   */
  private User createUser() {
    try {
      return this.userService.createUser(this.buildUser());
    } catch (DatabaseUserException e) {
      Notification.show("User could not be created");
      return null;
    }
  }

  private Student buildStudent(User user) {
    return Student.builder()
        .firstName(this.firstNameField.getValue())
        .lastName(this.lastNameField.getValue())
        .phone(this.phoneField.getValue())
        .user(user)
        .build();
  }

  private Company buildCompany(User user) {
    return Company.builder()
        .name(this.firstNameField.getValue())
        .phone(this.phoneField.getValue())
        .user(user)
        .build();
  }

  private User buildUser() {
    final User user =
        User.builder()
            .username(this.usernameField.getValue())
            .password(Encryption.sha256(this.passwordField.getValue()))
            .email(this.mailField.getValue())
            .build();
    final PermissionGroup group =
        this.groupService.findPermissionGroupByName(this.groupSelector.getValue());
    if (group != null) {
      user.addPermissionGroup(group);
    }
    return user;
  }

  private void doClearForm() {
    this.usernameField.clear();
    this.firstNameField.clear();
    this.lastNameField.clear();
    this.groupSelector.clear();
    this.mailField.clear();
    this.phoneField.clear();
    this.passwordField.clear();
  }

  private Component doCreateFormLayout() {
    final FormLayout formLayout = new FormLayout();
    formLayout.add(
        this.usernameField,
        this.passwordField,
        this.firstNameField,
        this.lastNameField,
        this.mailField,
        this.phoneField,
        this.groupSelector);
    formLayout.setColspan(this.usernameField, 1);
    formLayout.setColspan(this.firstNameField, 1);
    formLayout.setColspan(this.lastNameField, 1);
    formLayout.setColspan(this.mailField, 1);
    formLayout.setColspan(this.phoneField, 1);
    formLayout.setColspan(this.passwordField, 1);
    formLayout.setColspan(this.groupSelector, 1);
    this.saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    formLayout.add(new Span(""));
    formLayout.add(this.saveButton);
    formLayout.add(this.cancelButton);
    formLayout.setColspan(this.saveButton, 1);
    formLayout.setColspan(this.cancelButton, 1);
    return formLayout;
  }
}
