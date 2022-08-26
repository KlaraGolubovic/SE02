package org.hbrs.academicflow.view.routes.profile;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.hbrs.academicflow.util.Constants;
import org.hbrs.academicflow.view.common.layouts.AppView;
import org.hbrs.academicflow.view.routes.profile.component.EditOrganisationProfile;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Route(value = Constants.Pages.ORGANIZATION_PROFILE_EDIT_VIEW, layout = AppView.class)
@PageTitle("Unternehmensprofil bearbeiten")
@CssImport("./styles/views/main/main-view.css")
public class ProfileView extends Div {

  private final EditOrganisationProfile editProfile;

  @PostConstruct
  public void doInitialSetup() {
    this.add(this.editProfile);
  }
}
