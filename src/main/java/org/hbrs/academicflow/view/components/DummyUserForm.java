package org.hbrs.academicflow.view.components;

import com.google.common.collect.Lists;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.hbrs.academicflow.model.permission.PermissionGroupService;
import org.hbrs.academicflow.model.studentUser.StudUser;
import org.hbrs.academicflow.model.user.User;
import org.hbrs.academicflow.model.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Scope(value = "prototype")
public class DummyUserForm extends Div {

  private final Grid<User> userGrid = new Grid<>();

  private final Button deletedummies = new Button("Remove all dummy users");

  private final TextField idField = new TextField("Nutzername");
  private final TextField firstNameField = new TextField("Vorname");
  private final TextField lastNameField = new TextField("Nachname");
  private final TextField mailField = new TextField("E-Mail");
  private final TextField phoneField = new TextField("Telefonnummer");
  private final TextField passwordField = new TextField("Passwort");

  private final UserService userService;
  private final List<User> users = Lists.newCopyOnWriteArrayList();
  private final PermissionGroupService permissionService;


  @PostConstruct
  private void doPostConstruct() {

    deletedummies.getElement().getStyle().set("margin-left", "auto");
    this.add(doCreateUserSection());
    this.refreshUserGridData();
  }

  private Component doCreateUserSection() {
    addClassName("show-users-view");
    setInitialValues();
    Div div = new Div();
    div.add(new H3("All Users"));
    // -----------------------------------------------
    div.add(this.doCreateUserTable());

    // ----------------------------
    FormLayout formLayout = new FormLayout();
    formLayout.add(idField, mailField);
    formLayout.setColspan(idField, 1);
    formLayout.setColspan(mailField, 1);
    // ------------------------------------------
    HorizontalLayout actions = new HorizontalLayout();
    actions.add(saveDummyUserButton());
    actions.add(deletedummiesButton());
    formLayout.add(actions, 2);
    div.add(formLayout);
    return div;
  }

  private StudUser userFromFormValues() throws NoSuchAlgorithmException {
    final StudUser user = new StudUser();
    //user.setUser(this.idField.getValue());
    //todo: make sure we can create a user here
    /*this.permissionService
        .findPermissionGroupByName("student")
        .map(Lists::newArrayList)
        .ifPresent(user::setGroups);*/
    //user.setPassword(Encryption.sha256(this.passwordField.getValue()));
    user.setFirstName(this.firstNameField.getValue());
    user.setLastName(this.lastNameField.getValue());
    user.setPhone(this.phoneField.getValue());
    user.setEmail(this.mailField.getValue());
    return user;
  }

  private void refreshUserGridData() {
    // DIESE METHODE MACHT MAGISCHE SACHEN MIT DER TABELLE
    this.users.clear();
    this.users.addAll(this.userService.findAllUsers());
    this.userGrid.getDataProvider().refreshAll();
  }

  private Component doCreateUserTable() {
   /* userGrid.setDataProvider(new ListDataProvider<>(this.users));
    userGrid.addColumn(User::getId).setHeader("ID").setWidth("20px");
    userGrid.addColumn(User::getUsername).setHeader("Username");
    userGrid.addColumn(User::getEmail).setHeader("E-Mail").setWidth("180px");
    
    userGrid.addColumn(User::getFirstName).setHeader("First Name");
    userGrid.addColumn(User::getLastName).setHeader("Last Name");
    userGrid.addColumn(User::getDateOfBirth).setHeader("Birthdate");
    //userGrid.addColumn(User::getOccupation).setHeader("Occupation");

    userGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
    userGrid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
    userGrid.addThemeVariants(GridVariant.MATERIAL_COLUMN_DIVIDERS);*/
    return userGrid;
  }

  private Component deletedummiesButton() {
    deletedummies.addClickListener(
        event -> {
          List<User> all = userService.findAllUsers();
          boolean del = false;
          for (User user : all) {
            if (user.getEmail()
                .equals("dummy@mail.de")) { // remove this condition to delete everyone
              del = true;
              userService.deleteUser(user.getUsername());
            }
          }
          if (del) {
            Notification.show("Dummy users have been deleted.");
          } else {
            Notification.show("No dummy users were found.");
          }
          this.refreshUserGridData();
        });
    deletedummies.addThemeVariants(ButtonVariant.LUMO_ERROR);
    return deletedummies;
  }

  @SuppressWarnings("java:S106")
  private Component saveDummyUserButton() {
    Button save = new Button("Add dummy user");
    save.addClickListener(
        event -> {
          try {
            //userService.doCreateUser(this.userFromFormValues());
            Notification.show("User account has been created");
            this.refreshUserGridData();
            if(this.idField == null){
              throw new NoSuchAlgorithmException();
            }

          } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
          } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
          } catch (org.springframework.dao.DataIntegrityViolationException die) {
            System.err.println(die);
            if (die.getRootCause().getMessage().contains("email")) {
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

  private void setInitialValues() {
    idField.setValue("Username5");
    firstNameField.setValue("Dumbo");
    lastNameField.setValue("Dumbsen");
    mailField.setValue("dummy@mail.de");
    phoneField.setValue("11778892");
    passwordField.setValue("SportFan04");
  }
}
