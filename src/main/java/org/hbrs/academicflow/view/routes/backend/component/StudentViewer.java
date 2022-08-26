package org.hbrs.academicflow.view.routes.backend.component;

import com.google.common.collect.Lists;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.data.provider.ListDataProvider;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.hbrs.academicflow.control.student.StudentService;
import org.hbrs.academicflow.model.student.user.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Scope(value = "prototype")
public class StudentViewer extends Div {

  private static final String PX200 = "200px";
  private final transient StudentService studentService;
  private final Grid<Student> studentsGrid = new Grid<>();
  private final List<Student> students = Lists.newCopyOnWriteArrayList();

  @PostConstruct
  private void doPostConstruct() {
    addClassName("show-users-view");
    this.add(this.buildPermissionGroupSection());
  }

  private Component buildPermissionGroupSection() {
    final Div div = new Div();
    div.add(new H3("All Students"));
    div.add(this.studentsGrid);
    this.studentsGrid.setDataProvider(new ListDataProvider<>(this.students));
    this.studentsGrid.addColumn(Student::getId).setHeader("ID").setWidth("145px");
    this.studentsGrid.addColumn(Student::getFirstName).setHeader("Vorname").setWidth(PX200);
    this.studentsGrid.addColumn(Student::getLastName).setHeader("Nachname").setWidth(PX200);
    this.studentsGrid.addColumn(Student::getDateOfBirth).setHeader("Geburtsdatum")
        .setWidth("270px");
    this.studentsGrid.addColumn(Student::getPhone).setHeader("Telefonnummer").setWidth(PX200);
    this.studentsGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
    this.studentsGrid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
    this.studentsGrid.addThemeVariants(GridVariant.MATERIAL_COLUMN_DIVIDERS);
    // maybe add the user id of the students
    this.refreshTableData();
    return div;
  }

  private void refreshTableData() {
    this.students.clear();
    this.students.addAll(this.studentService.findAll());
    this.studentsGrid.getDataProvider().refreshAll();
  }
}
