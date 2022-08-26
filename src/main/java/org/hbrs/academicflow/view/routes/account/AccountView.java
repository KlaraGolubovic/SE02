package org.hbrs.academicflow.view.routes.account;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.hbrs.academicflow.util.Constants;
import org.hbrs.academicflow.view.common.layouts.AppView;
import org.hbrs.academicflow.view.routes.account.component.EditAccount;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Route(value = Constants.Pages.ACCOUNT_VIEW, layout = AppView.class)
@PageTitle("Account")
@CssImport("./styles/views/backend/show-users-view.css")
public class AccountView extends Div {

  private final EditAccount editAccount;

  @PostConstruct
  public void doInitialSetup() {
    this.add(this.editAccount);
  }
}
