package org.hbrs.academicflow.view.common.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class Navbar extends Div {

  public Component getNavbar() {
    return this.createHeaderContent();
  }

  /**
   * Creates the header content. Uses a HorizontalLayout to align the items. This is Vaadins default
   * layout.
   *
   * @return the component
   */
  private Component createHeaderContent() {
    HorizontalLayout layout = new HorizontalLayout();
    layout.setId("header");
    layout.getThemeList().set("dark", true);
    layout.setWidthFull();
    layout.setSpacing(false);
    layout.setAlignItems(FlexComponent.Alignment.CENTER);
    layout.setJustifyContentMode(FlexComponent.JustifyContentMode.EVENLY);
    // DrawerToggle is the Hamburger menu
    layout.add(new DrawerToggle());
    H1 viewTitle = new H1();
    viewTitle.setWidthFull();
    layout.add(viewTitle);
    // Internal layout
    HorizontalLayout topRightPanel = new HorizontalLayout();
    topRightPanel.setWidthFull();
    topRightPanel.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
    topRightPanel.setAlignItems(FlexComponent.Alignment.CENTER);
    // Hier kann später der Nutzername eingefügt werden
    H1 helloUser = new H1();
    topRightPanel.add(helloUser);
    // Logout-Button am rechts-oberen Rand.
    MenuBar bar = new MenuBar();
    // no Logout here
    topRightPanel.add(bar);
    layout.add(topRightPanel);
    return layout;
  }
}
