package org.hbrs.academicflow.view;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.hbrs.academicflow.view.components.DummyPermissionGroupForm;
import org.hbrs.academicflow.view.components.DummyUserForm;
import org.hbrs.academicflow.view.components.VerticalSpacerGenerator;
import org.hbrs.academicflow.view.layouts.PublicAppView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Scope;

/** https://vaadin.com/components/vaadin-grid/java-examples/header-and-footer */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Route(value = "DEVELOPMENT", layout = PublicAppView.class)
@PageTitle("Show Everything")
@CssImport("./styles/views/backend/show-users-view.css")
@Scope(value = "prototype")
public class BackendDevelopmentView extends Div {

  private final DummyUserForm duf;
  private final DummyPermissionGroupForm dpgf;

  @PostConstruct
  public void doInitialSetup() {
    try {
      this.add(duf);
      this.add(dpgf);
    } catch (Exception e) {
      e.printStackTrace();
      // Forward to error handling page
    }
  }
}
