package org.hbrs.academicflow.view.routes.backend.component;

import com.google.common.collect.Lists;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
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
import org.hbrs.academicflow.model.student.user.StudentRepository;
import org.hbrs.academicflow.model.user.User;
import org.hbrs.academicflow.util.Encryption;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Scope(value = "prototype")
public class DummyUserForm extends Div {

  private static final String EMAIL = "E-Mail";
  private final Grid<User> userGrid = new Grid<>();
  private final Button deleteDummiesButton = new Button("Remove all dummy users");
  private final TextField usernameField = new TextField("Nutzername");
  private final TextField firstNameField = new TextField("Vorname");
  private final TextField lastNameField = new TextField("Nachname");
  private final TextField mailField = new TextField(EMAIL);
  private final TextField phoneField = new TextField("Telefonnummer");
  private final TextField passwordField = new TextField("Passwort");
  private final transient UserService userService;
  private final transient StudentService studentService;
  private final transient CompanyService companyService;
  private final transient StudentRepository studentRepository;
  private final transient PermissionGroupService permissionService;
  private final List<User> users = Lists.newCopyOnWriteArrayList();

  @PostConstruct
  private void doPostConstruct() {
    this.deleteDummiesButton.getElement().getStyle().set("margin-left", "auto");
    this.add(this.buildUserSection());
    this.doRefreshUserGridTable();
  }

  private Component buildUserSection() {
    addClassName("show-users-view");
    setInitialValues();
    Div div = new Div();
    div.add(new H3("All Users"));
    // -----------------------------------------------
    div.add(this.buildUserTable());
    // ----------------------------
    FormLayout formLayout = new FormLayout();
    formLayout.add(this.usernameField, this.mailField);
    formLayout.setColspan(this.usernameField, 1);
    formLayout.setColspan(this.mailField, 1);
    // ------------------------------------------
    HorizontalLayout actions = new HorizontalLayout();
    actions.add(buildDummyUserButton());
    actions.add(buildDeleteDummyUsersButton());
    formLayout.add(actions, 2);
    div.add(formLayout);
    return div;
  }

  private @NotNull User buildUser() {
    final User user =
        User.builder()
            .username(this.usernameField.getValue())
            .password(Encryption.sha256(this.passwordField.getValue()))
            .email(this.mailField.getValue())
            .build();
    final PermissionGroup group = this.permissionService.findPermissionGroupByName("student");
    if (group != null) {
      user.addPermissionGroup(group);
    }
    return user;
  }

  private @NotNull Student buildStudent(User user) {
    return Student.builder()
        .user(user)
        .firstName(this.firstNameField.getValue())
        .lastName(this.lastNameField.getValue())
        .phone(this.phoneField.getValue())
        .build();
  }

  private void doRefreshUserGridTable() {
    // DIESE METHODE MACHT MAGISCHE SACHEN MIT DER TABELLE
    this.users.clear();
    this.users.addAll(this.userService.findAllUsers());
    this.userGrid.getDataProvider().refreshAll();
  }

  //
  private Component buildUserTable() {
    this.userGrid.setDataProvider(new ListDataProvider<>(this.users));
    this.userGrid.addColumn(User::getId).setHeader("ID").setWidth("20px");
    this.userGrid.addColumn(User::getUsername).setHeader("Username");
    this.userGrid.addColumn(User::getEmail).setHeader(EMAIL).setWidth("180px");
    this.userGrid
        .addComponentColumn(
            item ->
                new Button(
                    "Edit",
                    click -> {
                      final Dialog dialog = new Dialog();
                      final VerticalLayout dialogLayout = buildUserEditDialogLayout(dialog, item);
                      dialog.add(dialogLayout);
                      dialog.setModal(false);
                      dialog.setDraggable(true);
                      add(dialog);
                      dialog.open();
                    }))
        .setHeader("Bearbeiten")
        .setWidth("180px")
        .setKey("key");
    this.userGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
    this.userGrid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
    this.userGrid.addThemeVariants(GridVariant.MATERIAL_COLUMN_DIVIDERS);
    return this.userGrid;
  }

  public VerticalLayout buildUserEditDialogLayout(Dialog dialog, User user) {
    // FROM DOCS: https://vaadin.com/docs/v14/ds/components/dialog
    final H2 headline = new H2("Nutzer Bearbeiten");
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
    final TextField titleField = new TextField("Username");
    titleField.setValue(user.getUsername());
    final TextField emailField = new TextField(EMAIL);
    emailField.setValue(user.getEmail());
    final VerticalLayout fieldLayout = new VerticalLayout(titleField, emailField);
    fieldLayout.setSpacing(false);
    fieldLayout.setPadding(false);
    fieldLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
    final Button cancelButton = new Button("Cancel", e -> dialog.close());
    final Button saveButton = new Button("Save", e -> dialog.close());
    saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    final HorizontalLayout buttonLayout = new HorizontalLayout(cancelButton, saveButton);
    buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
    final VerticalLayout dialogLayout = new VerticalLayout(header, fieldLayout, buttonLayout);
    dialogLayout.setPadding(false);
    dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
    dialogLayout.getStyle().set("width", "300px").set("max-width", "100%");
    return dialogLayout;
  }

  private Component buildDeleteDummyUsersButton() {
    this.deleteDummiesButton.addClickListener(
        event -> {
          final AtomicBoolean deleted = new AtomicBoolean();
          this.userService.findAllUsers().stream()
              .filter(user -> user.getEmail().contains("dummy"))
              .forEach(
                  user -> {
                    final Student student = this.studentService.findStudentByUserID(user.getId());
                    if (student != null) {
                      this.studentRepository.delete(student);
                    }
                    final Company comp = this.companyService.findCompanyByFullUser(user);
                    if (comp != null) {
                      this.companyService.deleteCompany(comp);
                    }
                    this.userService.deleteByUsername(user.getUsername());
                    deleted.set(true);
                  });
          this.userService.deleteByUsername("exxeta");
          if (deleted.get()) {
            Notification.show("Dummy users have been deleted.");
          } else {
            Notification.show("No dummy users were found.");
          }
          this.doRefreshUserGridTable();
        });
    this.deleteDummiesButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
    return this.deleteDummiesButton;
  }

  private Component buildDummyUserButton() {
    final Button save = new Button("Add dummy user");
    save.addClickListener(
        event -> {
          try {
            final User user = createDummyUser();
            if (user == null) {
              Notification.show("User could not be created");
              return;
            }
            final Student student = this.studentService.doCreateStudent(this.buildStudent(user));
            if (student == null) {
              Notification.show("Student could not be created");
              return;
            }
            Notification.show("User account has been created");
            this.doRefreshUserGridTable();
          } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
          } catch (org.springframework.dao.DataIntegrityViolationException die) {
            log.error(die.getMessage());
            if (Objects.requireNonNull(die.getRootCause()).getMessage().contains("email")) {
              Notification.show("Error: email adress already in use: " + this.mailField.getValue());
            } else {
              Notification.show(
                  "Error: something that is not the email is restricting user-creation");
            }
          }
        });
    save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    return save;
  }

  /**
   * Handles the creation of the user in the DB uses the buildUser Method
   *
   * @return ``null`` on failure or passing on from createUser
   */
  private User createDummyUser() {
    try {
      return this.userService.createUser(this.buildUser());
    } catch (DatabaseUserException e) {
      return null;
    }
  }

  private void setInitialValues() {
    this.usernameField.setValue("Username5");
    this.firstNameField.setValue("Dumbo");
    this.lastNameField.setValue("Dumbsen");
    this.mailField.setValue("dummy@mail.de");
    this.phoneField.setValue("11778892");
    this.passwordField.setValue("SportFan04");
  }
}
