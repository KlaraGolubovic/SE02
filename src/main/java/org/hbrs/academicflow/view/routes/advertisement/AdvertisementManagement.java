package org.hbrs.academicflow.view.routes.advertisement;

import com.google.common.collect.Lists;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.hbrs.academicflow.control.advertisement.AdvertisementService;
import org.hbrs.academicflow.control.company.user.CompanyService;
import org.hbrs.academicflow.model.advertisement.AdvertisementDTO;
import org.hbrs.academicflow.model.company.user.Company;
import org.hbrs.academicflow.model.user.UserDTO;
import org.hbrs.academicflow.util.Constants;
import org.hbrs.academicflow.util.SessionAttributes;
import org.hbrs.academicflow.view.common.components.VerticalSpacerGenerator;
import org.hbrs.academicflow.view.common.layouts.AppView;
import org.hbrs.academicflow.view.routes.advertisement.component.EditableAdvertisement;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Route(value = Constants.Pages.ORGANIZATION_LIST_ADS, layout = AppView.class)
@PageTitle("Meine Jobanzeigen")
@CssImport("./styles/views/backend/show-users-view.css")
@CssImport("./styles/views/main/main-view.css")
@CssImport("./styles/views/main/advertisements.css")
public class AdvertisementManagement extends Div {

  private final List<AdvertisementDTO> advertisements = Lists.newCopyOnWriteArrayList();
  private final transient AdvertisementService advertisementService;
  private final transient CompanyService companyService;

  @PostConstruct
  private void init() {
    addClassName("show-users-view");
    this.advertisements.clear();
    this.advertisements.addAll(advertisementService.findAdvertisements());
    this.add(new VerticalSpacerGenerator("2vh").buildVerticalSpacer());
    this.add(this.buildScaffold());
    this.add(this.buildAdvertisementList());
  }

  private Component buildScaffold() {
    return new Div(this.buildTopLine());
  }

  private Component buildAdvertisementList() {
    final VerticalLayout layout = new VerticalLayout();
    final UserDTO user = SessionAttributes.getCurrentUser();
    if (user == null) {
      return layout;
    }
    final Company company = this.companyService.findCompanyByUser(user);
    if (company == null) {
      return layout;
    }
    this.advertisements.stream()
        .filter(advertisement -> advertisement.getCompany().getId().equals(company.getId()))
        .filter(AdvertisementDTO::getActive).forEach(a -> layout.add(new EditableAdvertisement(a)));
    return layout;
  }

  private Component buildTopLine() {
    final HorizontalLayout layout = new HorizontalLayout();
    layout.setWidthFull();
    layout.setHeight("30px");
    layout.setSpacing(false);
    layout.setAlignItems(FlexComponent.Alignment.CENTER);
    layout.setJustifyContentMode(FlexComponent.JustifyContentMode.EVENLY);
    layout.add(this.buildCreationButton());
    return layout;
  }

  private Component buildCreationButton() {
    final Button button = new Button("Jobanzeige erstellen");
    // Notification -> Anzeige ist raus!
    button.addClickListener(
        event -> UI.getCurrent().navigate(Constants.Pages.ORGANIZATION_CREATE_AD));
    return button;
  }
}