package org.hbrs.academicflow.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.*;

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
    // Ein paar Grund-Einstellungen. Alles wird in ein horizontales Layout gesteckt.
    HorizontalLayout layout = new HorizontalLayout();
    layout.setId("header");
    layout.getThemeList().set("dark", true);
    layout.setWidthFull();
    layout.setSpacing(false);
    layout.setAlignItems(FlexComponent.Alignment.CENTER);

    viewTitle = new H1();
    viewTitle.setWidthFull();
    viewTitle.addClassName("leftpad");
    layout.add(viewTitle);

    return layout;
  }

  @Override
  protected void afterNavigation() {
    super.afterNavigation();

    // Setzen des aktuellen Names des Tabs
    viewTitle.setText(getCurrentPageTitle());

    // Setzen des Vornamens von dem aktuell eingeloggten Benutzer

  }

  private String getCurrentPageTitle() {
    PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
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
  public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
    // THIS METHOD STOPS EXECUTION (CONDITIONALLY), WE DO NOT WANT THAT HERE.
    // IN the future maybe: if (REGISTRATION_ENABLED) or other features
  }
}
