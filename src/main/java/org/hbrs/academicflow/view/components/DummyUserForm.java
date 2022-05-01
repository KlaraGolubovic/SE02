package org.hbrs.academicflow.view.components;

import java.security.NoSuchAlgorithmException;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.TextField;

import org.hbrs.academicflow.model.user.User;
import org.hbrs.academicflow.model.user.UserRepository;

import com.google.common.collect.Lists;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.hbrs.academicflow.model.permission.PermissionGroup;
import org.hbrs.academicflow.model.permission.PermissionGroupService;
import org.hbrs.academicflow.model.user.User;
import org.hbrs.academicflow.model.user.UserService;
import org.hbrs.academicflow.util.Encryption;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DummyUserForm extends Div{
    
  private Grid<User> userGrid = new Grid<>();
  private final Button save = new Button("Add dummy user");
  private final Button deletedummies = new Button("Remove all dummy users");
  
  private final TextField idField = new TextField("Nutzername");
  private final TextField firstNameField = new TextField("Vorname");
  private final TextField lastNameField = new TextField("Nachname");
  private final TextField mailField = new TextField("E-Mail");
  private final TextField phoneField = new TextField("Telefonnummer");
  private final TextField passwordField = new TextField("Passwort");
  
  private UserService userService;
  private final List<User> users = Lists.newCopyOnWriteArrayList();
  private final PermissionGroupService permissionService;

@PostConstruct
  public void doInitialSetup() {
    addClassName("show-users-view");
    idField.setValue("Username5");
    firstNameField.setValue("Dumbo");
    lastNameField.setValue("Dumbsen");
    mailField.setValue("dummy@mail.de");
    phoneField.setValue("11778892");
    passwordField.setValue("SportFan04");
    this.users.addAll(this.userService.findAllUsers());
  }

  private User dummyUser() throws NoSuchAlgorithmException {
    final User user = new User();
    user.setUsername(this.idField.getValue());
    user.setFirstName(this.firstNameField.getValue());
    user.setLastName(this.lastNameField.getValue());
    user.setPhone(this.phoneField.getValue());

    user.setPassword(Encryption.sha256(this.passwordField.getValue()));

    user.setEmail(this.mailField.getValue());
    this.permissionService
        .findPermissionGroupByName("student")
        .map(Lists::newArrayList)
        .ifPresent(user::setGroups);
    return user;
  }


  public Component doCreateUserSection() {
    Div div = new Div();
    div.add(new H3("All Users"));
    div.add(this.doCreateUserTable());
    div.add(doCreateFormLayout());
    this.refreshUserGridData();
    return div;
  }
  private void refreshUserGridData() {
    // DIESE METHODE MACHT MAGISCHE SACHEN MIT DER TABELLE
    this.users.clear();
    this.users.addAll(this.userService.findAllUsers());
    this.userGrid.getDataProvider().refreshAll();
  }

  private Component doCreateUserTable() {
    userGrid = new Grid<>();
    userGrid.setDataProvider(new ListDataProvider<>(this.users));
    userGrid.addColumn(User::getId).setHeader("ID").setWidth("20px");
    userGrid.addColumn(User::getUsername).setHeader("Username");
    userGrid.addColumn(User::getFirstName).setHeader("First Name");
    userGrid.addColumn(User::getLastName).setHeader("Last Name");
    userGrid.addColumn(User::getEmail).setHeader("E-Mail").setWidth("180px");
    userGrid.addColumn(User::getDateOfBirth).setHeader("Birthdate");
    userGrid.addColumn(User::getOccupation).setHeader("Occupation");

    userGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
    userGrid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
    userGrid.addThemeVariants(GridVariant.MATERIAL_COLUMN_DIVIDERS);
    return userGrid;
  }

  private Component doCreateFormLayout() {
    FormLayout formLayout = new FormLayout();
    formLayout.add(idField, mailField);
    formLayout.setColspan(idField, 1);
    formLayout.setColspan(mailField, 1);

    formLayout.add(usertableActionDiv(), 2);
    return formLayout;
  }
  
  private Component usertableActionDiv() {
    HorizontalLayout div = new HorizontalLayout();
    save.addClickListener(
        event -> {
          try {
            userService.doCreateUser(this.dummyUser());
            Notification.show("User account has been created");
            this.refreshUserGridData();

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
    deletedummies.addClickListener(
        event -> {
          List<User> all = userService.findAllUsers();
          boolean del = false;
          for (User user : all) {
            if (user.getPhone().equals("11778892")) { // remove this condition to delete everyone
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

    deletedummies.getElement().getStyle().set("margin-left", "auto");
    deletedummies.addThemeVariants(ButtonVariant.LUMO_ERROR);
    div.add(save);
    div.add(deletedummies);
    return div;
  }

  public DummyUserForm(PermissionGroupService permissionService2, UserService userService2) {
    this.permissionService = permissionService2;
    this.userService = userService2;
  }


  
}
