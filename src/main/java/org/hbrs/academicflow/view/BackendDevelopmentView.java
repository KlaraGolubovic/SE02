package org.hbrs.academicflow.view;

import com.google.common.collect.Lists;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.hbrs.academicflow.model.permission.PermissionGroup;
import org.hbrs.academicflow.model.permission.PermissionGroupService;
import org.hbrs.academicflow.model.user.User;
import org.hbrs.academicflow.model.user.UserService;
import org.hbrs.academicflow.util.Encryption;
import org.hbrs.academicflow.view.components.DummyUserForm;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * https://vaadin.com/components/vaadin-grid/java-examples/header-and-footer
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Route(value = "DEVELOPMENT", layout = PublicAppView.class)
@PageTitle("Show Everything")
@CssImport("./styles/views/backend/show-users-view.css")
public class BackendDevelopmentView extends Div {

  private final PermissionGroupService permissionService;
  private final List<PermissionGroup> permissionGroups = Lists.newCopyOnWriteArrayList();
  private final TextField pgName = new TextField("Permisson Group name");
  private Grid<PermissionGroup> permissionGrid = new Grid<>();
  private final Button savePG = new Button("Add Permisson Group");
  private final UserService userService;
  private DummyUserForm duf;

  @PostConstruct
  public void doInitialSetup() {
    duf = new DummyUserForm(permissionService, userService);
    addClassName("show-users-view");
    this.permissionGroups.addAll(this.permissionService.findAll());
    add(duf.doCreateUserSection());
    add(this.doCreatePermissionGroupSection());
  }

  private Component doCreatePermissionGroupSection() {
    Div div = new Div();
    div.add(new H3("All Permission Groups"));
    this.doCreatePermissionGroupTable();
    div.add(this.permissionGrid);
    savePG.addClickListener(
        event -> {
          if (!pgName.getValue().equals("")) {
            PermissionGroup pg = new PermissionGroup();
            pg.setName(pgName.getValue());
            PermissionGroup newpg = permissionService.doCreatePermissionGroup(pg);
            if (newpg == null) {
              Notification.show("Permission Group could not be created");
            } else {
              Notification.show("Permission Group has been created");
              this.permissionGroups.add(pg);
              this.permissionGrid.getDataProvider().refreshAll();
            }
          }
        });
    pgName.getElement().getStyle().set("margin-left", "auto");
    savePG.getElement().getStyle().set("margin-right", "auto");
    FormLayout formLayout = new FormLayout();
    formLayout.add(pgName, savePG);
    formLayout.setColspan(pgName, 1);
    formLayout.setColspan(savePG, 1);

    div.add(formLayout);
    return div;
  }

  private Grid<PermissionGroup> doCreatePermissionGroupTable() {
    this.permissionGrid.setDataProvider(new ListDataProvider<>(this.permissionGroups));
    this.permissionGrid.addColumn(PermissionGroup::getName).setHeader("Name").setWidth("200px");
    this.permissionGrid
        .addColumn(permissionGroup -> permissionGroup.getUsers().size())
        .setHeader("Number of Users")
        .setWidth("200px");
    // ToDo: fix this to get the size of list instead of list itself
    return this.permissionGrid;
  }
}
