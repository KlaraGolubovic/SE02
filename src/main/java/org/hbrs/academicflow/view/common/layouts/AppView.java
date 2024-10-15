package org.hbrs.academicflow.view.common.layouts;

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
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import java.util.ArrayList;
import java.util.Optional;
import javax.annotation.PostConstruct;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hbrs.academicflow.control.AuthorizationService;
import org.hbrs.academicflow.control.LoginService;
import org.hbrs.academicflow.control.company.user.CompanyService;
import org.hbrs.academicflow.control.student.StudentService;
import org.hbrs.academicflow.model.company.user.Company;
import org.hbrs.academicflow.model.student.user.Student;
import org.hbrs.academicflow.model.user.UserDTO;
import org.hbrs.academicflow.util.Constants;
import org.hbrs.academicflow.util.Constants.Pages;
import org.hbrs.academicflow.util.SessionAttributes;
import org.hbrs.academicflow.view.common.components.TabGenerator;
import org.hbrs.academicflow.view.routes.advertisement.AdApplication;
import org.hbrs.academicflow.view.routes.advertisement.AdvertisementManagement;
import org.hbrs.academicflow.view.routes.advertisement.AdvertisementSearch;
import org.hbrs.academicflow.view.routes.advertisement.StudentApplicationView;
import org.hbrs.academicflow.view.routes.profile.ProfileView;
import org.hbrs.academicflow.view.routes.profile.StudentProfileView;
import org.hbrs.academicflow.view.routes.welcome.WelcomeView;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

/** The main view is a top-level placeholder for other views. */
@CssImport("./styles/views/main/main-view.css")
@Route("main")
@PWA(name = "AcademicFlow", shortName = "AcademicFlow", enableInstallPrompt = false)
@JsModule("./styles/shared-styles.js")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@EqualsAndHashCode(callSuper = false)
@Data
@Slf4j
public class AppView extends AppLayout implements BeforeEnterObserver {

  protected final transient StudentService studentService;
  protected final transient CompanyService companyService;
  protected final transient LoginService loginService;
  private Tabs menu;
  private H1 viewTitle;
  private H1 helloUser;
  private transient AuthorizationService authorizationControl;

  private Image logoSmall = new Image("images/logo.png", "AcademicFlow logo");

  @PostConstruct
  public void init() {
    this.authorizationControl = new AuthorizationService();
    logoSmall.setHeight("var(--lumo-size-l)");
    if (this.isDrawerOpened()) {
      this.logoSmall.setVisible(false);
    }
    this.menu = buildMenu();
    this.initUI();
  }

  private void initUI() {
    // Anzeige des Toggles über den Drawer
    setPrimarySection(Section.DRAWER);
    // Erstellung der horizontalen Statusleiste (Header)
    addToNavbar(true, this.buildHeaderContent());
    // Erstellung der vertikalen Navigationsleiste (Drawer)
    addToDrawer(this.buildDrawerContent(this.menu));
  }

  private boolean isUserLoggedIn() {
    // Falls der Benutzer nicht eingeloggt ist, dann wird er auf die Startseite
    // gelenkt
    final UserDTO user = SessionAttributes.getCurrentUser();
    if (user == null) {

      UI.getCurrent().navigate(Pages.LANDING_VIEW);
      return false;
    }
    return true;
  }

  /**
   * Erzeugung der horizontalen Leiste (Header).
   *
   * @return Header inhalt
   */
  private Component buildHeaderContent() {
    // Ein paar Grund-Einstellungen. Alles wird in ein horizontales Layout gesteckt.
    HorizontalLayout layout = new HorizontalLayout();
    layout.setId("header");
    layout.getThemeList().set("dark", true);
    layout.setWidthFull();
    layout.setSpacing(false);
    layout.setAlignItems(FlexComponent.Alignment.CENTER);
    layout.setJustifyContentMode(FlexComponent.JustifyContentMode.EVENLY);
    // Hinzufügen des Toogle ('Big Mac') zum Ein- und Ausschalten des Drawers
    DrawerToggle toggle = new DrawerToggle();
    toggle.addClickListener(event -> this.logoSmall.setVisible(!this.isDrawerOpened()));
    layout.add(toggle);
    this.viewTitle = new H1();
    this.viewTitle.setWidthFull();
    layout.add(this.viewTitle);
    this.logoSmall.addClickListener(
        clickEvent -> {
          UI ui = UI.getCurrent();
          ui.navigate(Constants.Pages.WELCOME_VIEW);
        });
    layout.add(this.logoSmall);
    // Interner Layout
    HorizontalLayout topRightPanel = new HorizontalLayout();
    topRightPanel.setWidthFull();
    topRightPanel.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
    topRightPanel.setAlignItems(FlexComponent.Alignment.CENTER);
    // Der Name des Users wird später reingesetzt, falls die Navigation stattfindet
    this.helloUser = new H1();
    topRightPanel.add(this.helloUser);
    // Logout-Button am rechts-oberen Rand.
    MenuBar bar = new MenuBar();
    bar.addItem("Abmelden", event -> logoutUser());
    // Button soll "navbar-button" als ID haben!
    topRightPanel.add(bar);
    layout.add(topRightPanel);
    return layout;
  }

  private void logoutUser() {
    this.getUI()
        .ifPresent(
            ui -> {
              this.loginService.logout();
              ui.getSession().close();
              ui.navigate(Constants.Pages.LANDING_VIEW);
              Notification n =
                  Notification.show("Erfolgreich abgemeldet", 9000, Position.BOTTOM_CENTER);
              n.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            });
  }

  /**
   * Hinzufügen der vertikalen Leiste (Drawer) Diese besteht aus dem Logo ganz oben links sowie den
   * Menu-Einträgen (menu items). Die Menu Items sind zudem verlinkt zu den internen Tab-Components.
   *
   * @param menu Inhalte für den Drawer
   * @return Drawer Content links
   */
  private Component buildDrawerContent(Tabs menu) {
    VerticalLayout layout = new VerticalLayout();
    layout.addClassName("drawer");
    layout.setId("main-drawer-left");
    layout.setSizeFull();
    layout.setPadding(false);
    layout.setSpacing(false);
    layout.getThemeList().set("spacing-s", true);
    layout.setAlignItems(FlexComponent.Alignment.STRETCH);
    HorizontalLayout logoLayout = new HorizontalLayout();
    logoLayout.setId("logo");
    logoLayout.setAlignItems(FlexComponent.Alignment.CENTER);
    Image logoImage = new Image("images/af_logo_small.png", "AcademicFlow logo");
    logoImage.addClickListener(
        clickEvent -> UI.getCurrent().navigate(Constants.Pages.WELCOME_VIEW));
    logoLayout.add(logoImage);
    // Hinzufügen des Menus inklusive der Tabs
    layout.add(logoLayout, menu);
    return layout;
  }

  /**
   * Erzeugung des Menu auf der vertikalen Leiste (Drawer)
   *
   * @return Menü links
   */
  private Tabs buildMenu() {
    // Anlegen der Grundstruktur
    final Tabs tabs = new Tabs();
    tabs.setOrientation(Tabs.Orientation.VERTICAL);
    tabs.addThemeVariants(TabsVariant.LUMO_MINIMAL);
    tabs.setId("tabs");
    // Anlegen der einzelnen Menuitems
    tabs.add(this.buildMenuItems());
    return tabs;
  }

  private Component[] buildMenuItems() {
    final UserDTO user = SessionAttributes.getCurrentUser();
    if (user == null) {
      return new Component[] {};
    }
    final ArrayList<Tab> tabs = new ArrayList<>();
    // hier werden die Tabs manuell gebaut
    tabs.add(buildTab("Startseite", WelcomeView.class));
    if (this.authorizationControl.hasPermissionGroup(user, "Organisation")) {
      tabs.add(buildTab("Unternehmensprofil", ProfileView.class));
      tabs.add(buildTab("Meine Jobanzeigen", AdvertisementManagement.class));
      tabs.add(buildTab("Eingegangene Bewerbungen", AdApplication.class));
    } else {
      tabs.add(buildTab("Mein Profil", StudentProfileView.class));
      tabs.add(buildTab("Meine Bewerbungen", StudentApplicationView.class));
    }
    tabs.add(buildTab("Alle Jobanzeigen", AdvertisementSearch.class));
    return tabs.toArray(new Tab[0]);
  }

  private Tab buildTab(String text, Class<? extends Component> navigationTarget) {
    return TabGenerator.buildTab(text, navigationTarget);
  }

  @Override
  protected void afterNavigation() {
    super.afterNavigation();
    // Falls der Benutzer nicht eingeloggt ist, dann wird er auf die Startseite
    // gelenkt

    if (!isUserLoggedIn()) {
      return;
    }
    // Der aktuell-selektierte Tab wird gehighlighted.
    getTabForComponent(getContent()).ifPresent(this.menu::setSelectedTab);
    // Setzen des aktuellen Names des Tabs
    this.viewTitle.setText(getPageTitle());
    // Setzen des Vornamens von dem aktuell eingeloggten Benutzer
    this.helloUser.setText("Hi, " + this.getUserName());
  }

  private @NotNull Optional<Tab> getTabForComponent(Component component) {
    if (this.menu == null) {
      return Optional.empty();
    }
    return this.menu
        .getChildren()
        .filter(tab -> ComponentUtil.getData(tab, Class.class).equals(component.getClass()))
        .findFirst()
        .map(Tab.class::cast);
  }

  private @NotNull String getPageTitle() {
    PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
    return title == null ? "" : title.value();
  }

  private @NotNull String getUserName() {
    final UserDTO user = SessionAttributes.getCurrentUser();
    if (user == null) {
      return "USER_NOT_FOUND";
    }
    return user.getUsername();
  }

  /**
   * Methode wird vor der eigentlichen Darstellung der UI-Components aufgerufen. Hier kann man die
   * finale Darstellung noch abbrechen, wenn z.B. der Nutzer nicht eingeloggt ist. Nachzulesen in
   * der Dokumentation von Vaadin ,wie es funktioniert Nachzulesen in unserer Dokumentation, wie wir
   * es einsetzen
   *
   * @param event before navigation event with event details
   */
  @Override
  public void beforeEnter(BeforeEnterEvent event) {
    final UserDTO user = SessionAttributes.getCurrentUser();
    if (user == null) {
      event.rerouteTo(Constants.Pages.LANDING_VIEW);
      log.info("Benutzer nicht eingeloggt: " + event.getNavigationTarget());
      return;
    }
    Company company;
    Student student;

    student = this.studentService.findStudentByUserID(user.getId());
    company = this.companyService.findCompanyByUser(user);
    if (student != null) {
      log.info("Showing AppView logged in as Student {}", student.getFirstName());
    } else {
      if (company == null) {
        log.info("Showing AppView logged in as neither {}", user.getUsername());
        if (Constants.organizationOnlyRefs().contains(event.getLocation().getPath())) {
          log.info(
              "User is not associated with a company. Redirecting to profile view."
                  + event.getLocation().getPath()
                  + " for user "
                  + user.getUsername());
          event.rerouteTo(Constants.Pages.WELCOME_VIEW);
        }
      } else {
        log.info("Showing AppView logged in as Company {}", company.getName());
      }
    }
  }
}
