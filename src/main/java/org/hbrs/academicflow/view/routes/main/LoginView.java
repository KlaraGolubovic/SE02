package org.hbrs.academicflow.view.routes.main;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.hbrs.academicflow.control.LoginService;
import org.hbrs.academicflow.control.exception.DatabaseUserException;
import org.hbrs.academicflow.model.user.UserDTO;
import org.hbrs.academicflow.util.Constants;
import org.hbrs.academicflow.view.common.layouts.PublicAppView;
import org.springframework.beans.factory.annotation.Autowired;

@CssImport("./styles/views/main/login.css")
@CssImport("./styles/views/main/main-view.css")
@JsModule("./styles/shared-styles.js")
@Route(value = Constants.Pages.LOGIN_VIEW, layout = PublicAppView.class)
@PageTitle("Anmelden")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

  @Autowired private transient LoginService loginService;

  @PostConstruct
  private void init() {
    setSizeFull();
    final LoginForm component = new LoginForm();
    Image logo = new Image("icons/icon.png", "Logo");
    logo.setWidth("108%");
    component.addLoginListener(
        event -> {
          boolean isAuthenticated = false;
          try {
            isAuthenticated =
                this.loginService.authentication(event.getUsername(), event.getPassword());
          } catch (DatabaseUserException databaseException) {
            Dialog dialog = new Dialog();
            dialog.add(new Text(databaseException.getReason()));
            dialog.setWidth("400px");
            dialog.setHeight("150px");
            dialog.open();
          }
          if (isAuthenticated) {
            grabAndSetUserIntoSession();
            UI.getCurrent().navigate(Constants.Pages.WELCOME_VIEW);
          } else {
            // Kann noch optimiert werden
            component.setError(true);
          }
        });
    LoginI18n i18n = new LoginI18n();
    LoginI18n.Header header = new LoginI18n.Header();
    header.setTitle("Anmeldung");
    header.setDescription("Hier anmelden.");
    i18n.setHeader(header);
    LoginI18n.Form form = new LoginI18n.Form();
    form.setForgotPassword("Passwort vergessen?");
    form.setPassword("Passwort");
    form.setTitle("Anmelden");
    form.setSubmit("Anmelden");
    form.setUsername("Benutzername");
    i18n.setForm(form);
    component.setI18n(i18n);
    add(component);
    final Button button = new Button("Noch keinen Account? Jetzt Registrieren!");
    button.addClickListener(clickEvent -> UI.getCurrent().navigate(Constants.Pages.REGISTER_VIEW));
    add(button);
    this.setAlignItems(Alignment.CENTER);
  }

  private void grabAndSetUserIntoSession() {
    final UserDTO user = this.loginService.getCurrentUser();
    UI.getCurrent().getSession().setAttribute(Constants.CURRENT_USER, user);
  }

  @Override
  public void beforeEnter(BeforeEnterEvent event) {
    if (this.loginService.getCurrentUser() != null) {
      this.grabAndSetUserIntoSession();
      UI.getCurrent().navigate(Constants.Pages.WELCOME_VIEW);
    }
  }
}
