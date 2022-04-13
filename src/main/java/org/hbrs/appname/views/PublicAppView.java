package org.hbrs.appname.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
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

import org.hbrs.appname.control.AuthorizationControl;
import org.hbrs.appname.dtos.UserDTO;
import org.hbrs.appname.util.Globals;
import org.hbrs.appname.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Optional;

/**
 * The main view is a top-level placeholder for other views.
 */
@CssImport("./styles/views/main/main-view.css")
@Route("publicmain")
@JsModule("./styles/shared-styles.js")
public class PublicAppView extends AppLayout implements BeforeEnterObserver {

    private Tabs menu;
    private H1 viewTitle;
    private H1 helloUser;

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
     * Methode wird vor der eigentlichen Darstellung der UI-Components aufgerufen.
     * Hier kann man die finale Darstellung noch abbrechen, wenn z.B. der Nutzer
     * nicht eingeloggt ist
     * Dann erfolgt hier ein ReDirect auf die Login-Seite. Eine Navigation (Methode
     * navigate)
     * ist hier nicht möglich, da die finale Navigation noch nicht stattgefunden
     * hat.
     * Diese Methode in der AppLayout sichert auch den un-authorisierten Zugriff auf
     * die innerliegenden
     * Views (hier: ShowCarsView und EnterCarView) ab.
     *
     */
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
       
        //THIS METHOD STOPS EXECUTION (CONDITIONALLY), WE DO NOT WANT THAT HERE.

    }
}
