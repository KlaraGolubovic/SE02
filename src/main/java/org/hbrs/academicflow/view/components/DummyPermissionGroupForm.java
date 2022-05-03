package org.hbrs.academicflow.view.components;

import com.google.common.collect.Lists;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.hbrs.academicflow.model.permission.PermissionGroup;
import org.hbrs.academicflow.model.permission.PermissionGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Scope(value = "prototype")
public class DummyPermissionGroupForm extends Div {

  private final PermissionGroupService permissionService;
  private final List<PermissionGroup> permissionGroups = Lists.newCopyOnWriteArrayList();
  private final TextField pgName = new TextField("Permisson Group name");
  private final Grid<PermissionGroup> permissionGrid = new Grid<>();
  private final Button savePG = new Button("Add Permisson Group");

  @PostConstruct
  private void doPostConstruct() {
    addClassName("show-users-view");
    this.add(this.doCreatePermissionGroupSection());

    pgName.getElement().getStyle().set("margin-left", "auto");
    savePG.getElement().getStyle().set("margin-right", "auto");
  }

  private Component doCreatePermissionGroupSection() {
    Div div = new Div();
    div.add(new H3("All Permission Groups"));
    div.add(this.permissionGrid);
    this.permissionGrid.setDataProvider(new ListDataProvider<>(this.permissionGroups));
    this.permissionGrid.addColumn(PermissionGroup::getName).setHeader("Name").setWidth("200px");
    this.permissionGrid
        .addColumn(permissionGroup -> permissionGroup.getUsers().size())
        .setHeader("Number of Users")
        .setWidth("200px");
    savePG.addClickListener(
        event -> {
          String name = pgName.getValue();
          if (name.equals("")) {
            Notification.show("Name cannot be empty.");
            return;
          }
          if (nameConflict(name)) {
            Notification.show(
                "Permission Group could not be created. " + name + " is already in use.");
            return;
          }
          PermissionGroup pg = new PermissionGroup();
          pg.setName(pgName.getValue());
          permissionService.doCreatePermissionGroup(pg);
          Notification.show("Permission Group has been created");
          refreshTableData();
        });
    FormLayout formLayout = new FormLayout();
    formLayout.add(pgName, savePG);
    formLayout.setColspan(pgName, 1);
    formLayout.setColspan(savePG, 1);

    div.add(formLayout);
    refreshTableData();
    return div;
  }

  private boolean nameConflict(String groupName) {
    return permissionGroups.stream().anyMatch(group -> group.getName().equals(groupName));
    // anyMatch for Conflict could also be noneMatch for OK
  }

  private void refreshTableData() {
    this.permissionGroups.clear();
    this.permissionGroups.addAll(this.permissionService.findAllUncached());
    this.permissionGrid.getDataProvider().refreshAll();
  }

}
