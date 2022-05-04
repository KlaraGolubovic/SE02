package org.hbrs.academicflow.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.PropertyId;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.hbrs.academicflow.model.permission.PermissionGroup;
import org.hbrs.academicflow.model.permission.PermissionGroupService;
import org.hbrs.academicflow.model.studentUser.StudUser;
import org.hbrs.academicflow.model.user.User;
import org.hbrs.academicflow.model.user.UserService;
import org.hbrs.academicflow.util.Constants;
import org.hbrs.academicflow.util.Encryption;
import org.hbrs.academicflow.view.layouts.PublicAppView;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Route(value = Constants.Pages.REGISTER_VIEW, layout = PublicAppView.class)
@PageTitle("Registration")
@CssImport("./styles/views/entercar/enter-car-view.css")
public class RegisterView extends Div {
  private final Button save = new Button("Save");
  private final Button cancel = new Button("Cancel");

  private final TextField idField = new TextField("Nutzername");
  private final TextField firstNameField = new TextField("Vorname");
  private final TextField lastNameField = new TextField("Nachname");
  private final TextField mailField = new TextField("E-Mail");
  private final TextField phoneField = new TextField("Telefonnummer");
  private final TextField passwordField = new TextField("Passwort");

  @PropertyId("roles")
  private final ComboBox<String> groupSelector = new ComboBox<>("roles");

  private final UserService userService;
  private final PermissionGroupService groupService;

  @PostConstruct
  public void doInitialSetup() {
    this.groupSelector.setDataProvider(
        new ListDataProvider<>(groupService.findPermissionGroupNames()));

    VerticalLayout layout = new VerticalLayout();
    layout.setWidth("80%");

    try {
      addClassName("register-view");
      layout.add(doCreateTitle());
      layout.add(groupSelector);
      layout.add(doCreateFormLayout());
      layout.add(doCreateButtonLayout());
    } catch (Exception e) {
      System.err.println("Error occurred adding layout");
    }

    try {
      doClearForm();
      cancel.addClickListener(event -> doClearForm());
      save.addClickListener(
          event -> {
            try {
              userService.doCreateUser(this.doCreateUser());
              Notification.show("User account has been created");
              doClearForm();
            } catch (NoSuchAlgorithmException e) {
              e.printStackTrace();
            } catch (IllegalArgumentException e) {
              System.err.println(e.getMessage());
            }
          });
    } catch (Exception e) {
      System.err.println("Error occurred registering Listeners to Form Buttons");
    }

    add(layout);
  }

  private User doCreateUser() throws NoSuchAlgorithmException {
    final User user = new User();
    user.setUsername(this.idField.getValue());
   
    user.setPassword(Encryption.sha256(this.passwordField.getValue()));

    user.setEmail(this.mailField.getValue());
    this.groupService
        .findPermissionGroupByName(this.groupSelector.getValue())
        .ifPresent((PermissionGroup e) -> setPermissionGroupOntoUser(e, user));
    StudUser studentOfNewUser = new StudUser();    
    studentOfNewUser.setFirstName(this.firstNameField.getValue());
    studentOfNewUser.setLastName(this.lastNameField.getValue());
    studentOfNewUser.setPhone(this.phoneField.getValue());
    studentOfNewUser.setUser(user);
    return user;
  }

  private void setPermissionGroupOntoUser(PermissionGroup e, User user) {
    List<PermissionGroup> x = new ArrayList<>();
    x.add(e);
    user.setGroups(x);
  }

  private void doClearForm() {
    this.idField.clear();
    this.firstNameField.clear();
    this.lastNameField.clear();
    this.groupSelector.clear();
    this.mailField.clear();
    this.phoneField.clear();
    this.passwordField.clear();
  }

  private Component doCreateTitle() {
    return new H3("Registration");
  }

  private Component doCreateFormLayout() {
    FormLayout formLayout = new FormLayout();
    formLayout.add(
        idField,
        firstNameField,
        lastNameField,
        groupSelector,
        mailField,
        phoneField,
        passwordField);
    formLayout.setColspan(idField, 1);
    formLayout.setColspan(firstNameField, 1);
    formLayout.setColspan(lastNameField, 1);
    formLayout.setColspan(mailField, 1);
    formLayout.setColspan(phoneField, 1);
    formLayout.setColspan(passwordField, 1);
    return formLayout;
  }

  private Component doCreateButtonLayout() {
    HorizontalLayout buttonLayout = new HorizontalLayout();
    buttonLayout.addClassName("button-layout");
    save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    buttonLayout.add(save);
    buttonLayout.add(cancel);
    return buttonLayout;
  }
}
