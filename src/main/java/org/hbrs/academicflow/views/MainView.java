package org.hbrs.academicflow.views;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import org.hbrs.academicflow.control.LoginControl;
import org.hbrs.academicflow.control.exception.DatabaseUserException;
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

    LoginForm component = new LoginForm();

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

    add(component);
    Button button = new Button(" Not a user yet? Register now! ");

    button.addClickListener(clickEvent -> UI.getCurrent().navigate(Constants.Pages.REGISTER_VIEW));

    add(button);
    this.setAlignItems(Alignment.CENTER);
  }

  private void grabAndSetUserIntoSession() {
    UserDTO userDTO = loginControl.getCurrentUser();
    UI.getCurrent().getSession().setAttribute(Constants.CURRENT_USER, userDTO);
  }

  private void navigateToMainPage() {
    // Navigation zur Startseite, hier auf die Teil-Komponente Show-Cars.
    // Die anzuzeigende Teil-Komponente kann man noch individualisieren, je nach
    // Rolle,
    // die ein Benutzer besitzt
    UI.getCurrent().navigate(Constants.Pages.REGISTER_VIEW);
  }
}
