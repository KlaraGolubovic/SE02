package org.hbrs.academicflow.view;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import javax.annotation.PostConstruct;
import org.hbrs.academicflow.controller.LoginControl;
import org.hbrs.academicflow.controller.exception.DatabaseUserException;
import org.hbrs.academicflow.model.user.dto.UserDTO;
import org.hbrs.academicflow.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * View zur Darstellung der Startseite. Diese zeigt dem Benutzer ein Login-Formular an. ToDo:
 * Integration einer Seite zur Registrierung von Benutzern
 */
@Route(value = "")
@RouteAlias(value = "login")
public class MainView extends VerticalLayout {

  @Autowired private LoginControl loginControl;

  // ToDo: Add registry as mentioned in #1
  public MainView() {
    setSizeFull();
    Div blue = new Div();
    blue.addClassName("backColorBlue");
    LoginForm component = new LoginForm();
    component.getElement().getClassList().add("backColorBlue");
    component.addLoginListener(
        e -> {
          boolean isAuthenticated = false;
          try {
            isAuthenticated = loginControl.doAuthenticate(e.getUsername(), e.getPassword());
          } catch (DatabaseUserException databaseException) {
            Dialog dialog = new Dialog();
            dialog.add(new Text(databaseException.getReason()));
            dialog.setWidth("400px");
            dialog.setHeight("150px");
            dialog.open();
          }
          if (isAuthenticated) {
            grabAndSetUserIntoSession();
            navigateToMainPage();

          } else {
            // Kann noch optimiert werden
            component.setError(true);
          }
        });
    blue.add(component);
    add(blue);
    Button button = new Button("Not a user yet? Register now!");
    button.addClickListener(clickEvent -> UI.getCurrent().navigate(Constants.Pages.REGISTER_VIEW));
    add(button);
    this.setAlignItems(Alignment.CENTER);
  }

  private void grabAndSetUserIntoSession() {
    UserDTO userDTO = loginControl.getCurrentUser();
    UI.getCurrent().getSession().setAttribute(Constants.CURRENT_USER, userDTO);
  }

  private void navigateToMainPage() {
    // Navigation zur Startseite
    UI.getCurrent().navigate(Constants.Pages.WELCOME_VIEW);
  }
}
