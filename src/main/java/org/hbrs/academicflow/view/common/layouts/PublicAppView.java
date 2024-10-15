package org.hbrs.academicflow.view.common.layouts;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.hbrs.academicflow.util.Constants.Pages;
import org.hbrs.academicflow.view.common.components.LabeledButtonCollection;
import org.jetbrains.annotations.NotNull;

/** The main view is a top-level placeholder for other views. */
@CssImport("./styles/views/main/main-view.css")
@Route("publicmain")
@JsModule("./styles/shared-styles.js")
public class PublicAppView extends AppLayout implements BeforeEnterObserver {

  private H1 viewTitle;

  public PublicAppView() {
    setUpUI();
  }

  public void setUpUI() {
    // Erstellung der horizontalen Statusleiste (Header)
    addToNavbar(true, createHeaderContent());
  }

  /**
   * Erzeugung der horizontalen Leiste (Header).
   *
   * @return
   */
  private Component createHeaderContent() {
    HorizontalLayout layout = new HorizontalLayout();
    layout.setId("header");
    layout.getStyle().set("padding-left", "var(--lumo-space-l)");
    layout.getStyle().set("padding-right", "var(--lumo-space-l)");
    layout.getThemeList().set("dark", true);
    layout.setWidthFull();
    layout.setSpacing(false);
    layout.setAlignItems(FlexComponent.Alignment.CENTER);
    layout.setJustifyContentMode(FlexComponent.JustifyContentMode.EVENLY);
    this.viewTitle = new H1();
    this.viewTitle.setWidthFull();
    layout.add(this.viewTitle);
    Image logoSmall = new Image("images/af_logo_small.png", "AcademicFlow logo");
    logoSmall.setHeight("var(--lumo-size-l)");
    layout.add(logoSmall);
    // Interner Layout
    HorizontalLayout topRightPanel = new HorizontalLayout();
    topRightPanel.setWidthFull();
    topRightPanel.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
    topRightPanel.setAlignItems(FlexComponent.Alignment.CENTER);
    // Login- und Registrieren Button am rechts-oberen Rand.
    Button login = LabeledButtonCollection.generalButton("Anmelden", Pages.LOGIN_VIEW);
    topRightPanel.add(login);
    Button register = LabeledButtonCollection.registerButton("Registrieren");
    register.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    topRightPanel.add(register);
    layout.add(topRightPanel);
    return layout;
  }

  @Override
  protected void afterNavigation() {
    super.afterNavigation();
    // Setzen des aktuellen Names des Tabs
    this.viewTitle.setText(getCurrentPageTitle());
    // Setzen des Vornamens von dem aktuell eingeloggten Benutzer
  }

  private @NotNull String getCurrentPageTitle() {
    final PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
    return title == null ? "" : title.value();
  }

  @Override
  /**
   * Methode wird vor der eigentlichen Darstellung der UI-Components aufgerufen. Hier kann man die
   * finale Darstellung noch abbrechen, wenn z.B. der Nutzer nicht eingeloggt ist Dann erfolgt hier
   * ein ReDirect auf die Login-Seite. Eine Navigation (Methode navigate) ist hier nicht möglich, da
   * die finale Navigation noch nicht stattgefunden hat. Diese Methode in der AppLayout sichert auch
   * den un-authorisierten Zugriff auf die innerliegenden Views (hier: ShowCarsView und
   * EnterCarView) ab.
   */
  @SuppressWarnings({"java:S125"})
  public void beforeEnter(BeforeEnterEvent event) {
    // THIS METHOD STOPS EXECUTION (CONDITIONALLY), WE DO NOT WANT THAT HERE.
    // IN the future maybe: if (REGISTRATION_ENABLED) or other features
  }
}
