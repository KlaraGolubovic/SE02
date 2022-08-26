package org.hbrs.academicflow.view.routes.account.component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.hbrs.academicflow.control.student.StudentService;
import org.hbrs.academicflow.model.student.user.Student;
import org.hbrs.academicflow.model.user.UserDTO;
import org.hbrs.academicflow.util.SessionAttributes;
import org.hbrs.academicflow.view.common.components.VerticalSpacerGenerator;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class EditAccount extends Div {

  private final transient StudentService studentService;
  private final Button save = new Button("Save");
  private final Button cancel = new Button("Cancel");
  private final Span usernameField = new Span("Nutzername");
  private final Span mailField = new Span("E-Mail");
  private final TextField firstNameField = new TextField("Vorname");
  private final TextField lastNameField = new TextField("Nachname");
  private final TextField phoneField = new TextField("Telefonnummer");

  @PostConstruct
  void doSetLayout() {
    this.add(new VerticalSpacerGenerator("1em").buildVerticalSpacer());
    addClassName("show-users-view");
    final UserDTO user = SessionAttributes.getCurrentUser();
    if (user == null) {
      return;
    }
    this.setValuesIntoFormFromUser(user);
    final Student student = this.studentService.findStudentByUserID(user.getId());
    if (student == null) {
      return;
    }
    this.setValuesIntoFormFromStudent(student);
    this.usernameField.setTitle("Nutzername");
    this.mailField.setTitle("E-Mail");
    this.save.addClickListener(
        click -> {
          student.setFirstName(this.firstNameField.getValue());
          student.setLastName(this.lastNameField.getValue());
          student.setPhone(this.phoneField.getValue());
          this.studentService.doUpdateStudent(student);
        });
    final FormLayout formLayout = new FormLayout();
    formLayout.add(
        this.usernameField,
        this.mailField,
        this.phoneField,
        this.firstNameField,
        this.lastNameField,
        this.save,
        this.cancel);
    formLayout.setColspan(this.usernameField, 2);
    formLayout.setColspan(this.firstNameField, 1);
    formLayout.setColspan(this.lastNameField, 1);
    formLayout.setColspan(this.mailField, 1);
    formLayout.setColspan(this.phoneField, 1);
    formLayout.setColspan(this.mailField, 1);
    formLayout.setColspan(this.save, 1);
    formLayout.setColspan(this.cancel, 1);
    this.add(formLayout);
  }

  private void setValuesIntoFormFromUser(UserDTO user) {
    if (user == null) {
      return;
    }
    this.usernameField.setText("Nutzername: " + user.getUsername());
    this.mailField.setText("E-Mail: " + user.getEmail());
  }

  private void setValuesIntoFormFromStudent(Student student) {
    if (student == null) {
      return; // guard statement
    }
    this.firstNameField.setValue(student.getFirstName());
    this.lastNameField.setValue(student.getLastName());
    this.phoneField.setValue(student.getPhone());
  }
}
