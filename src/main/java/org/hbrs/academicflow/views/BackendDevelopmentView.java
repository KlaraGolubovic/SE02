package org.hbrs.academicflow.views;

import com.google.common.collect.Lists;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.RequiredArgsConstructor;

import org.hbrs.academicflow.model.permission.PermissionGroup;
import org.hbrs.academicflow.model.permission.PermissionGroupService;
import org.hbrs.academicflow.model.user.User;
import org.hbrs.academicflow.model.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Darstellung einer Tabelle (bei Vaadin: ein Grid) zur Anzeige von Autos.
 * Hier wurden nur grundlegende Elemente verarbeitet. Weitere Optionen (z.B.
 * weitere Filter-Möglichkeiten) kann man hier entnehmen:
 * https://vaadin.com/components/vaadin-grid/java-examples/header-and-footer
 *
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Route(value = "DEVELOPMENT", layout = PublicAppView.class)
@PageTitle("Show Everything")
@CssImport("./styles/views/backend/show-users-view.css")
public class BackendDevelopmentView extends Div {
    private final UserService userService;
    private final PermissionGroupService permissionService;
    private final List<User> users = Lists.newCopyOnWriteArrayList();

    private final List<PermissionGroup> permissionGroups = Lists.newCopyOnWriteArrayList();

    @PostConstruct
    public void doInitialSetup() {
        addClassName("show-users-view");

        this.users.addAll(this.userService.findAllUsers());
        this.permissionGroups.addAll(this.permissionService.findAll());
        add(this.doCreateUserSection());
        add(this.doCreatePermissionGroupSection());
    }

    private Component doCreatePermissionGroupSection() {
        Div div = new Div();
        // Titel überhalb der Tabelle
        div.add(new H3("All Permission Groups"));
        // Hinzufügen der Tabelle (bei Vaadin: ein Grid)
        div.add(this.doCreatePermissionGroupTable());
        return div;
    }

    private Component doCreatePermissionGroupTable() {
        Grid<PermissionGroup> grid = new Grid<>();
        ListDataProvider<PermissionGroup> dataProvider = new ListDataProvider<>(this.permissionGroups);
        grid.setDataProvider(dataProvider);
        grid.addColumn(PermissionGroup::getName).setHeader("Name").setWidth("200px");
        grid.addColumn((PermissionGroup::getUsers)).setHeader("Number of Users").setWidth("200px");
        // ToDo: fix this to get the size of list instead of list itself
        return grid;
    }

    private Component doCreateUserSection() {
        Div div = new Div();
        // Titel überhalb der Tabelle
        div.add(new H3("All Users"));
        // Hinzufügen der Tabelle (bei Vaadin: ein Grid)
        div.add(this.doCreateUserTable());
        return div;
    }

    private Component doCreateUserTable() {
        Grid<User> grid = new Grid<>();
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        grid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
        ListDataProvider<User> dataProvider = new ListDataProvider<>(this.users);
        grid.setDataProvider(dataProvider);
        grid.addColumn(User::getId).setHeader("ID").setWidth("20px");
        grid.addColumn(User::getUserid).setHeader("Username");
        grid.addColumn(User::getFirstName).setHeader("First Name");
        grid.addColumn(User::getLastName).setHeader("Last Name");
        grid.addColumn(User::getEmail).setHeader("E-Mail").setWidth("180px");
        grid.addColumn(User::getDateOfBirth).setHeader("Birthdate");
        grid.addColumn(User::getOccupation).setHeader("Occupation");
        return grid;
    }

}
