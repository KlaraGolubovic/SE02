package org.hbrs.academicflow.view.routes.backend;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.hbrs.academicflow.view.common.layouts.PublicAppView;
import org.hbrs.academicflow.view.routes.backend.component.ApplicationListViewer;
import org.hbrs.academicflow.view.routes.backend.component.DemoDummyDataCreator;
import org.hbrs.academicflow.view.routes.backend.component.DummyUserForm;
import org.hbrs.academicflow.view.routes.backend.component.OrganisationViewer;
import org.hbrs.academicflow.view.routes.backend.component.PermissionGroupAdministration;
import org.hbrs.academicflow.view.routes.backend.component.StudentViewer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

/**
 * https://vaadin.com/components/vaadin-grid/java-examples/header-and-footer
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Route(value = "DEVELOPMENT", layout = PublicAppView.class)
@PageTitle("Show Everything")
@CssImport("./styles/views/backend/show-users-view.css")
@Scope(value = "prototype")
public class BackendDevelopmentView extends Div {

  private final DummyUserForm dummyUserForm;
  private final StudentViewer studentViewer;
  private final PermissionGroupAdministration administration;
  private final ApplicationListViewer applyList;
  private final OrganisationViewer organisationViewer;
  private final DemoDummyDataCreator demoDummyDataCreator;

  @PostConstruct
  public void doInitialSetup() {
    this.applyList.showAll();
    this.add(this.dummyUserForm);
    this.add(this.administration);
    this.add(this.studentViewer);
    this.add(this.organisationViewer);
    this.add(this.applyList);
    this.add(this.demoDummyDataCreator);
  }
}
