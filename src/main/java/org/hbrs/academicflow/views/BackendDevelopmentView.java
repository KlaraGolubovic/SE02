package org.hbrs.academicflow.views;

import com.google.common.collect.Lists;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.RequiredArgsConstructor;
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
@CssImport("./styles/views/showcars/show-cars-view.css")
public class BackendDevelopmentView extends Div {
    private final UserService userService;
    private List<User> users = Lists.newCopyOnWriteArrayList();

    @PostConstruct
    public void doInitialSetup() {
        addClassName("show-users-view");
        // Auslesen alle abgespeicherten Autos aus der DB (über das Control)
        this.users.addAll(this.userService.findAllUsers());
        // Titel überhalb der Tabelle
        add(this.doCreateTitle());
        // Hinzufügen der Tabelle (bei Vaadin: ein Grid)
        add(this.doCreateUserTable());
    }

    private Component doCreateUserTable() {
        Grid<User> grid = new Grid<>();
        // Befüllen der Tabelle mit den zuvor ausgelesenen Autos
        ListDataProvider<User> dataProvider = new ListDataProvider<>(this.users);
        grid.setDataProvider(dataProvider);
        grid.addColumn(User::getId).setHeader("ID").setWidth("20px");
        grid.addColumn(User::getFirstName).setHeader("First Name");

        grid.addColumn(User::getLastName).setHeader("Last Name");
        grid.addColumn(User::getEmail).setHeader("E-Mail").setWidth("180px");
        grid.addColumn(User::getDateOfBirth).setHeader("Birthdate");
        grid.addColumn(User::getOccupation).setHeader("Occupation");
        return grid;
    }

    private Component doCreateTitle() {
        return new H3("All Users");
    }
}
