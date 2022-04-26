package org.hbrs.academicflow.view.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class Navbar extends Div {
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
    H1 viewTitle = new H1();
    viewTitle.setWidthFull();
    layout.add(viewTitle);

    // Interner Layout
    HorizontalLayout topRightPanel = new HorizontalLayout();
    topRightPanel.setWidthFull();
    topRightPanel.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
    topRightPanel.setAlignItems(FlexComponent.Alignment.CENTER);

    // Der Name des Users wird später reingesetzt, falls die Navigation stattfindet
    H1 helloUser = new H1();
    topRightPanel.add(helloUser);

    // Logout-Button am rechts-oberen Rand.
    MenuBar bar = new MenuBar();
    //MenuItem item = bar.addItem("Logout", e -> logoutUser());
    topRightPanel.add(bar);

    layout.add(topRightPanel);
    return layout;
    }
}
