package org.hbrs.se2.project.hellocar.views;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import org.hbrs.se2.project.hellocar.control.ManageCarControl;
import org.hbrs.se2.project.hellocar.control.UserControl;
import org.hbrs.se2.project.hellocar.dtos.RolleDTO;
import org.hbrs.se2.project.hellocar.dtos.impl.CarDTOImpl;
import org.hbrs.se2.project.hellocar.dtos.UserDTO;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.hbrs.se2.project.hellocar.dtos.impl.UserDTOImpl;
import org.hbrs.se2.project.hellocar.util.Globals;

import java.util.ArrayList;
import java.util.List;

@Route(value = Globals.Pages.REGISTER_VIEW, layout = PublicAppView.class)
@PageTitle("Registration")
@CssImport("./styles/views/entercar/enter-car-view.css")
public class RegisterView extends Div {

    private TextField id = new TextField("id");
    private TextField firstname = new TextField("fn");
    private TextField lastname = new TextField("ln");

    private List<RolleDTO> roles = new ArrayList<>();

    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");

    private Binder<UserDTOImpl> binder = new Binder<>(UserDTOImpl.class);

    @SuppressWarnings({ "java:S106" })
    public RegisterView(UserControl userService) {
        VerticalLayout layout = new VerticalLayout();
        layout.setWidth("80%");
        try {
            addClassName("register-view");
            layout.add(createTitle());
            layout.add(createFormLayout());
            layout.add(createButtonLayout());
        } catch (Exception e) {
            System.out.println("Error occurred adding layout");
        }

        try {

            // Default Mapping of Cars attributes and the names of this View based on names
            // Source:
            // https://vaadin.com/docs/flow/binding-data/tutorial-flow-components-binder-beans.html
            binder.bindInstanceFields(this);
            clearForm();

        } catch (Exception e) {
            System.out.println("Error occurred adding bindings");
        }

        try {

            // Registrierung eines Listeners Nr. 1 (moderne Variante mit Lambda-Expression)

            cancel.addClickListener(event -> clearForm());
            save.addClickListener(e -> {
                userService.createUser(binder.getBean());
                Notification.show("User account has been created");
                clearForm();
            });
        } catch (Exception e) {
            System.out.println("Error occurred registering Listeners to Form Buttons");
        }
        add(layout);
    }

    private void clearForm() {
        binder.setBean(new UserDTOImpl());
    }

    private Component createTitle() {
        return new H3("Registration");
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(id, firstname, lastname);
        formLayout.setColspan(id, 1);
        formLayout.setColspan(firstname, 1);
        formLayout.setColspan(lastname, 1);
        return formLayout;
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save);
        buttonLayout.add(cancel);
        return buttonLayout;
    }

}
