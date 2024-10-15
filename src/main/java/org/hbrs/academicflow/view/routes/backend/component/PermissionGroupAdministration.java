package org.hbrs.academicflow.view.routes.backend.component;

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
import org.hbrs.academicflow.control.permission.PermissionGroupService;
import org.hbrs.academicflow.model.permission.PermissionGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Scope(value = "prototype")
public class PermissionGroupAdministration extends Div {

  private final transient PermissionGroupService permissionService;
  private final List<PermissionGroup> permissionGroups = Lists.newCopyOnWriteArrayList();
  private final TextField groupNameField = new TextField("Permisson Group name");
  private final Grid<PermissionGroup> groupGrid = new Grid<>();
  private final Button saveGroupButton = new Button("Add Permisson Group");

  @PostConstruct
  private void doPostConstruct() {
    addClassName("show-users-view");
    this.add(this.doCreatePermissionGroupSection());
    this.groupNameField.getElement().getStyle().set("margin-left", "auto");
    this.saveGroupButton.getElement().getStyle().set("margin-right", "auto");
  }

  private Component doCreatePermissionGroupSection() {
    Div div = new Div();
    div.add(new H3("All Permission Groups"));
    div.add(this.groupGrid);
    this.groupGrid.setDataProvider(new ListDataProvider<>(this.permissionGroups));
    this.groupGrid.addColumn(PermissionGroup::getName).setHeader("Name").setWidth("200px");
    this.groupGrid
        .addColumn(permissionGroup -> permissionGroup.getUsers().size())
        .setHeader("Number of Users")
        .setWidth("200px");
    this.saveGroupButton.addClickListener(
        event -> {
          String name = groupNameField.getValue();
          if (name.equals("")) {
            Notification.show("Name cannot be empty.");
            return;
          }
          if (this.isNameAlreadyInUse(name)) {
            Notification.show(
                "Permission Group could not be created. " + name + " is already in use.");
            return;
          }
          final PermissionGroup permissionGroup = new PermissionGroup();
          permissionGroup.setName(groupNameField.getValue());
          this.permissionService.save(permissionGroup);
          Notification.show("Permission Group has been created");
          refreshTableData();
        });
    final FormLayout formLayout = new FormLayout();
    formLayout.add(this.groupNameField, this.saveGroupButton);
    formLayout.setColspan(this.groupNameField, 1);
    formLayout.setColspan(this.saveGroupButton, 1);
    div.add(formLayout);
    refreshTableData();
    return div;
  }

  private boolean isNameAlreadyInUse(String groupName) {
    return this.permissionGroups.stream().anyMatch(group -> group.getName().equals(groupName));
  }

  private void refreshTableData() {
    this.permissionGroups.clear();
    this.permissionGroups.addAll(this.permissionService.findAllUncached());
    this.groupGrid.getDataProvider().refreshAll();
  }
}
