package org.hbrs.academicflow.view.routes.advertisement.component;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.hbrs.academicflow.control.advertisement.AdvertisementMapperImpl;
import org.hbrs.academicflow.control.apply.ApplyService;
import org.hbrs.academicflow.model.apply.Apply;
import org.hbrs.academicflow.util.Constants.Pages;
import org.hbrs.academicflow.view.common.layouts.AppView;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Route(value = Pages.STUDENT_APPLICATION_VISIBLE, layout = AppView.class)
@PageTitle("Bewerbung")
public class VisibleApplication extends Div implements HasUrlParameter<String> {

  private final transient ApplyService applyService;
  private UUID applicationID;

  public void init() {
    VerticalLayout layout = new VerticalLayout();
    layout.setWidth("80%");
    layout.setMaxWidth("1400px");
    Apply apply = applyService.findByApplicationID(applicationID);
    H2 h2 =
        new H2(
            "Bewerbung von "
                + apply.getStudentName()
                + " für "
                + apply.getCompanyName()
                + " am "
                + apply.getFormattedDate());
    layout.add(h2);
    H3 header = new H3("Bewerbungstext:");
    Paragraph paragraph = new Paragraph();
    paragraph.setText(apply.getNote());
    layout.add(header, paragraph);

    layout.add(
        new VisibleAd(apply.getAdvertisement())
            .actionButtonForAd(new AdvertisementMapperImpl().toDTO(apply.getAdvertisement())));
    add(layout);
  }

  @Override
  public void setParameter(BeforeEvent beforeEvent, String parameter) {
    if (parameter == null) {
      applicationID = null;
    } else {
      applicationID = UUID.fromString(parameter);
      this.init();
    }
  }
}
