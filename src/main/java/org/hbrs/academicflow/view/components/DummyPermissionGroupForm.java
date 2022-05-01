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
import lombok.RequiredArgsConstructor;
import org.hbrs.academicflow.model.permission.PermissionGroup;
import org.hbrs.academicflow.model.permission.PermissionGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@ComponentScan(
    basePackages = {"org.hbrs.academicflow.model.permission", "org.hbrs.academicflow.model.user"})
public class DummyPermissionGroupForm extends Div {

  private final PermissionGroupService permissionService;
  private final List<PermissionGroup> permissionGroups = Lists.newCopyOnWriteArrayList();
  private final TextField pgName = new TextField("Permisson Group name");
  private Grid<PermissionGroup> permissionGrid = new Grid<>();
  private final Button savePG = new Button("Add Permisson Group");
  
  public Component doCreatePermissionGroupSection() {
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
