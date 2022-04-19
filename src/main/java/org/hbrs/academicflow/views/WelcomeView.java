package org.hbrs.academicflow.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.PropertyId;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.hbrs.academicflow.model.permission.PermissionGroupService;
import org.hbrs.academicflow.model.user.UserService;
import org.hbrs.academicflow.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Route(value = Constants.Pages.WELCOME_VIEW, layout = AppView.class)
@PageTitle("Welcome")
@CssImport("./styles/views/entercar/enter-car-view.css")
public class WelcomeView extends Div {

  @PropertyId("roles")
  private final ComboBox<String> groupSelector = new ComboBox<>("roles");

  private final UserService userService;
  private final PermissionGroupService groupService;

  @PostConstruct
  public void doInitialSetup() {
    this.groupSelector.setDataProvider(
        new ListDataProvider<>(groupService.findPermissionGroupNames()));

    VerticalLayout layout = new VerticalLayout();
    layout.setWidth("80%");

    layout.add(doCreateTitle());

    add(layout);
  }

  private Component doCreateTitle() {
    return new H3("Welcome");
  }
}
