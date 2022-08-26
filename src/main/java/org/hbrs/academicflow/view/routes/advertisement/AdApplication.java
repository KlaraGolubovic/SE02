package org.hbrs.academicflow.view.routes.advertisement;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.hbrs.academicflow.util.Constants;
import org.hbrs.academicflow.view.common.layouts.AppView;
import org.hbrs.academicflow.view.routes.backend.component.ApplicationListViewer;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Route(value = Constants.Pages.ADAPPLICATION, layout = AppView.class)
@PageTitle("Eingegangene Bewerbungen")
public class AdApplication extends Div {

  private final ApplicationListViewer applyList;

  @PostConstruct
  public void doInit() {
    this.add(applyList);
  }
}
