package org.hbrs.academicflow.view.routes.main;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import org.hbrs.academicflow.control.LoginService;
import org.hbrs.academicflow.model.user.UserDTO;
import org.hbrs.academicflow.util.Constants;
import org.hbrs.academicflow.util.Constants.Pages;
import org.hbrs.academicflow.view.common.components.LabeledButtonCollection;
import org.hbrs.academicflow.view.common.layouts.PublicAppView;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "", layout = PublicAppView.class)
@RouteAlias(value = "landing", layout = PublicAppView.class)
// @CssImport("./styles/views/backend/show-users-view.css")
@CssImport("./styles/views/main/main-view.css")
@PageTitle("")
public class LandingView extends VerticalLayout implements BeforeEnterObserver {

  @Autowired private transient LoginService loginService;

  public LandingView() {
    setSizeFull();
    HorizontalLayout outerframe = new HorizontalLayout();
    this.getWidth();
    outerframe.setSpacing(false); // Compact layout
    Div frame = new Div();
    frame.setWidth("150%");
    outerframe.add(frame);
    outerframe.add(new DirectContact());
    Span landingSpan = new Span();
    landingSpan.add(new H1("Willkommen bei Academic Flow!"));
    frame.add(landingSpan);
    Span fliessend = new Span();
    fliessend.add(new H2("Für den fließenden Übergang von Studium zur Karriere"));
    frame.add(fliessend);
    Span vision = new Span();
    vision.add(
        new Paragraph(
            "Mit unserer Anwendung \"Academic Flow\" möchten wir Studierenden und interessierten"
                + " Unternehmen eine einfache und unkomplizierte Plattform zur Jobsuche bieten!"
                + " Egal ob für einen Werkstudentenjob, oder eine Teilzeitstelle während der"
                + " Bachelorarbeit - auf unserer Website finden Unternehmen und Studierende"
                + " zueinander und somit kann der Weg für eine tolle Zusammenarbeit geschaffen"
                + " werden!"));
    frame.add(vision);
    frame.add(new Span(new H2("Erstelle jetzt dein kostenloses Konto")));
    Span registerText = new Span();
    registerText.add(
        new Paragraph(
            "Du hast noch kein Benutzerkonto? Dann erstelle jetzt innerhalb weniger Minuten ein"
                + " Benutzerkonto."));
    frame.add(registerText);
    Button register = LabeledButtonCollection.registerButton("Registrieren");
    register.setId("registerbutton");
    frame.add(register);
    frame.add(new Span(new Paragraph("Oder melde dich mit deinem bestehendem Benutzerkonto an.")));
    frame.add(LabeledButtonCollection.generalButton("Anmelden", Pages.LOGIN_VIEW));
    this.add(outerframe);
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