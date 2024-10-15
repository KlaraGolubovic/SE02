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
import org.hbrs.academicflow.control.advertisement.AdvertisementService;
import org.hbrs.academicflow.control.company.profile.CompanyProfileService;
import org.hbrs.academicflow.control.company.user.CompanyService;
import org.hbrs.academicflow.model.company.user.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Scope(value = "prototype")
public class OrganisationViewer extends Div {

  private static final String PX200 = "200px";
  private final transient CompanyService companyService;
  private final transient CompanyProfileService companyProfileService;
  private final transient AdvertisementService advertisementService;
  private final Grid<Company> organisationsGrid = new Grid<>();
  private final List<Company> organisations = Lists.newCopyOnWriteArrayList();

  @PostConstruct
  private void doPostConstruct() {
    addClassName("show-users-view");
    this.add(this.buildPermissionGroupSection());
  }

  private Component buildPermissionGroupSection() {
    final Div div = new Div();
    div.add(new H3("All Organisations"));
    div.add(this.organisationsGrid);
    this.organisationsGrid.setDataProvider(new ListDataProvider<>(this.organisations));
    this.organisationsGrid.addColumn(Company::getId).setHeader("ID").setWidth("145px");
    this.organisationsGrid.addColumn(Company::getName).setHeader("Name").setWidth(PX200);
    this.organisationsGrid.addColumn(this::hasProfile).setHeader("Hat Profil").setWidth(PX200);
    this.organisationsGrid
        .addColumn(this::getAmountOfAds)
        .setHeader("Menge der Anzeigen")
        .setWidth(PX200);
    this.organisationsGrid.addColumn(Company::getPhone).setHeader("Telefonnummer").setWidth(PX200);
    this.organisationsGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
    this.organisationsGrid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
    this.organisationsGrid.addThemeVariants(GridVariant.MATERIAL_COLUMN_DIVIDERS);
    this.refreshTableData();
    return div;
  }

  private int getAmountOfAds(Company company) {
    return this.advertisementService.findAdvertisementsByCompanyId(company.getId()).size();
  }

  private boolean hasProfile(Company company) {
    return this.companyProfileService.findCompanyByCompanyId(company.getId()) != null;
  }

  private void refreshTableData() {
    this.organisations.clear();
    this.organisations.addAll(this.companyService.findAll());
    this.organisationsGrid.getDataProvider().refreshAll();
  }
}
