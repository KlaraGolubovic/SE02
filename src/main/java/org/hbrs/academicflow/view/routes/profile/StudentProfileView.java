package org.hbrs.academicflow.view.routes.profile;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.hbrs.academicflow.control.student.StudentService;
import org.hbrs.academicflow.util.Constants;
import org.hbrs.academicflow.util.SessionAttributes;
import org.hbrs.academicflow.view.common.layouts.AppView;
import org.hbrs.academicflow.view.routes.profile.component.EditStudentProfile;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Route(value = Constants.Pages.STUDENT_PROFILE_EDIT_VIEW, layout = AppView.class)
@PageTitle("Mein Profil")
@CssImport("./styles/views/backend/show-users-view.css")
public class StudentProfileView extends Div {

  private final EditStudentProfile editStudentProfile;
  private final transient StudentService studentService;

  @PostConstruct
  public void doInitialSetup() {
    if (SessionAttributes.isCurrentUserStudent(studentService)) {
      this.add(this.editStudentProfile);
    } else {
      this.add(new H1("Du bist kein Student"));
    }
  }
}
