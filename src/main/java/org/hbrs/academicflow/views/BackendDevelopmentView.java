package org.hbrs.academicflow.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.hbrs.academicflow.control.BackendControl;
import org.hbrs.academicflow.model.user.User;

import java.util.List;

/**
 * Darstellung einer Tabelle (bei Vaadin: ein Grid) zur Anzeige von Autos.
 * Hier wurden nur grundlegende Elemente verarbeitet. Weitere Optionen (z.B.
 * weitere Filter-Möglichkeiten) kann man hier entnehmen:
 * https://vaadin.com/components/vaadin-grid/java-examples/header-and-footer
 *
 */
@Route(value = "DEVELOPMENT", layout = PublicAppView.class)
@PageTitle("Show Everything")
@CssImport("./styles/views/showcars/show-cars-view.css")
public class BackendDevelopmentView extends Div {

    private List<User> personList;

    public BackendDevelopmentView(BackendControl backend) {
        addClassName("show-cars-view");

        // Auslesen alle abgespeicherten Autos aus der DB (über das Control)
        personList = backend.getAllUsers();

        // Titel überhalb der Tabelle
        add(this.createTitle());

        // Hinzufügen der Tabelle (bei Vaadin: ein Grid)
        add(this.createGridTable());
    }

    private Component createGridTable() {
        Grid<User> grid = new Grid<>();

        // Befüllen der Tabelle mit den zuvor ausgelesenen Autos
        ListDataProvider<User> dataProvider = new ListDataProvider<>(
                personList);
        grid.setDataProvider(dataProvider);

        grid.addColumn(User::getId).setHeader("ID").setWidth("20px");
        grid.addColumn(User::getFirstName).setHeader("First Name");
        grid.addColumn(
                User::getLastName)
                .setHeader("Last Name");
        grid.addColumn(
                User::getEmail)
                .setHeader("E-Mail").setWidth("180px");
        grid.addColumn(
                User::getDateOfBirth)
                .setHeader("Birthdate");
        grid.addColumn(
                User::getOccupation)
                .setHeader("Occupation");
        return grid;
    }

    private Component createTitle() {
        return new H3("All Users");
    }

}
