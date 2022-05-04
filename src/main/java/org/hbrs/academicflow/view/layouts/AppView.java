package org.hbrs.academicflow.view.layouts;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.PWA;
import java.util.Optional;
import javax.annotation.PostConstruct;
import org.hbrs.academicflow.controller.AuthorizationControl;
import org.hbrs.academicflow.model.user.dto.UserDTO;
import org.hbrs.academicflow.util.Constants;
import org.hbrs.academicflow.util.Logger;

/** The main view is a top-level placeholder for other views. */
@CssImport("./styles/views/main/main-view.css")
@Route("main")
@PWA(name = "AcademicFlow", shortName = "AcademicFlow", enableInstallPrompt = false)
@JsModule("./styles/shared-styles.js")
public class AppView extends AppLayout implements BeforeEnterObserver {

  private Tabs menu;
  private H1 viewTitle;
  private H1 helloUser;

  private AuthorizationControl authorizationControl;

  @PostConstruct
  public void doInitialSetup() {
    UserDTO current = getCurrentUser();
    if (current == null) {
      Logger.noUserGiven();
    } else {
      Logger.userLoggedInMessage(current.getFirstName());
      // ToDo: decide weather UserDTO should have the UserID String as well.
      setUpUI();
    }
  }

  public void setUpUI() {
    // Anzeige des Toggles über den Drawer
    setPrimarySection(Section.DRAWER);

    // Erstellung der horizontalen Statusleiste (Header)
    addToNavbar(true, createHeaderContent());

    // Erstellung der vertikalen Navigationsleiste (Drawer)
    menu = createMenu();
    addToDrawer(createDrawerContent(menu));
  }

  private boolean checkIfUserIsLoggedIn() {
    // Falls der Benutzer nicht eingeloggt ist, dann wird er auf die Startseite
    // gelenkt
    UserDTO userDTO = this.getCurrentUser();
    if (userDTO == null) {
      UI.getCurrent().navigate(Constants.Pages.LOGIN_VIEW);
      return false;
    }
    return true;
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
    layout.setJustifyContentMode(FlexComponent.JustifyContentMode.EVENLY);

    // Hinzufügen des Toogle ('Big Mac') zum Ein- und Ausschalten des Drawers
    layout.add(new DrawerToggle());
    viewTitle = new H1();
    viewTitle.setWidthFull();
    layout.add(viewTitle);

    // Interner Layout
    HorizontalLayout topRightPanel = new HorizontalLayout();
    topRightPanel.setWidthFull();
    topRightPanel.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
    topRightPanel.setAlignItems(FlexComponent.Alignment.CENTER);

    // Der Name des Users wird später reingesetzt, falls die Navigation stattfindet
    helloUser = new H1();
    topRightPanel.add(helloUser);

    // Logout-Button am rechts-oberen Rand.
    MenuBar bar = new MenuBar();
    bar.addItem("logout", e -> logoutUser());
    topRightPanel.add(bar);
    layout.add(topRightPanel);
    return layout;
  }

  private void logoutUser() {
    this.getUI()
        .ifPresent(
            ui -> {
              ui.getSession().close();
              ui.getPage().setLocation("/");
            });
  }

  /**
   * Hinzufügen der vertikalen Leiste (Drawer) Diese besteht aus dem Logo ganz oben links sowie den
   * Menu-Einträgen (menu items). Die Menu Items sind zudem verlinkt zu den internen Tab-Components.
   *
   * @param menu
   * @return
   */
  private Component createDrawerContent(Tabs menu) {
    VerticalLayout layout = new VerticalLayout();
    layout.setSizeFull();
    layout.setPadding(false);
    layout.setSpacing(false);
    layout.getThemeList().set("spacing-s", true);
    layout.setAlignItems(FlexComponent.Alignment.STRETCH);

    HorizontalLayout logoLayout = new HorizontalLayout();
    logoLayout.setId("logo");
    logoLayout.setAlignItems(FlexComponent.Alignment.CENTER);
    logoLayout.add(new Image("images/logo.png", "AcademicFlow logo"));
    logoLayout.add(new H1("AcademicFlow"));

    // Hinzufügen des Menus inklusive der Tabs
    layout.add(logoLayout, menu);
    return layout;
  }

  /**
   * Erzeugung des Menu auf der vertikalen Leiste (Drawer)
   *
   * @return
   */
  private Tabs createMenu() {

    // Anlegen der Grundstruktur
    final Tabs tabs = new Tabs();
    tabs.setOrientation(Tabs.Orientation.VERTICAL);
    tabs.addThemeVariants(TabsVariant.LUMO_MINIMAL);
    tabs.setId("tabs");

    // Anlegen der einzelnen Menuitems
    tabs.add(createMenuItems());
    return tabs;
  }

  private Component[] createMenuItems() {
    // Abholung der Referenz auf den Authorisierungs-Service
    authorizationControl = new AuthorizationControl();

    // Jeder User sollte Autos sehen können, von daher wird dieser schon mal erzeugt
    // und
    // und dem Tabs-Array hinzugefügt. In der Methode createTab wird ein (Key,
    // Value)-Pair übergeben:
    // Key: der sichtbare String des Menu-Items
    // Value: Die UI-Component, die nach dem Klick auf das Menuitem angezeigt wird.
    Tab[] tabs = new Tab[] {};

    // ToDo für die Teams: Weitere Tabs aus ihrem Projekt hier einfügen!

    return tabs;
  }

  private static Tab createTab(String text, Class<? extends Component> navigationTarget) {
    final Tab tab = new Tab();
    tab.add(new RouterLink(text, navigationTarget));
    ComponentUtil.setData(tab, Class.class, navigationTarget);
    return tab;
  }

  @Override
  protected void afterNavigation() {
    super.afterNavigation();

    // Falls der Benutzer nicht eingeloggt ist, dann wird er auf die Startseite
    // gelenkt
    if (!checkIfUserIsLoggedIn()) return;

    // Der aktuell-selektierte Tab wird gehighlighted.
    getTabForComponent(getContent()).ifPresent(menu::setSelectedTab);

    // Setzen des aktuellen Names des Tabs
    viewTitle.setText(getCurrentPageTitle());

    // Setzen des Vornamens von dem aktuell eingeloggten Benutzer
    helloUser.setText("Hello, " + this.getCurrentNameOfUser());
  }

  private Optional<Tab> getTabForComponent(Component component) {
    return menu.getChildren()
        .filter(tab -> ComponentUtil.getData(tab, Class.class).equals(component.getClass()))
        .findFirst()
        .map(Tab.class::cast);
  }

  private String getCurrentPageTitle() {
    PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
    return title == null ? "" : title.value();
  }

  private String getCurrentNameOfUser() {
    return getCurrentUser().getFirstName();
  }

  private UserDTO getCurrentUser() {
    return (UserDTO) UI.getCurrent().getSession().getAttribute(Constants.CURRENT_USER);
  }

  @Override
  /**
   * Methode wird vor der eigentlichen Darstellung der UI-Components aufgerufen. Hier kann man die
   * finale Darstellung noch abbrechen, wenn z.B. der Nutzer nicht eingeloggt ist. Nachzulesen in
   * der Dokumentation von Vaadin ,wie es funktioniert Nachzulesen in unserer Dokumentation, wie wir
   * es einsetzen
   */
  public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
    if (getCurrentUser() == null) {
      beforeEnterEvent.rerouteTo(Constants.Pages.LOGIN_VIEW);
      // ToDo: Hier ggf später erweitern um SomethingWentWrongView
    }
  }
}
