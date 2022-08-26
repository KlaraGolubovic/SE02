package org.hbrs.academicflow.view.routes.backend.component;

import com.google.common.collect.Lists;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.data.provider.ListDataProvider;
import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.hbrs.academicflow.control.apply.ApplyService;
import org.hbrs.academicflow.model.apply.Apply;
import org.hbrs.academicflow.model.user.UserDTO;
import org.hbrs.academicflow.util.SessionAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Scope(value = "prototype")
public class ApplicationListViewer extends Div {

  private static final String COLWIDTH = "220px";
  private final transient ApplyService applyService;
  private final Grid<Apply> applyGrid = new Grid<>();
  private final transient List<Apply> applications = Lists.newCopyOnWriteArrayList();
  private transient UserDTO currentUser;
  private boolean showall = false;

  @PostConstruct
  private void doPostConstruct() {
    addClassName("show-users-view");
    this.add(this.doCreateApplyListViewerSection());
  }

  private Component doCreateApplyListViewerSection() {
    Div div = new Div();
    div.add(new H3("Bewerbungen"));
    this.currentUser = SessionAttributes.getCurrentUser();
    // only use the ads of the user
    this.applyGrid.setDataProvider(new ListDataProvider<>(this.applications));
    H4 headerStudent = new H4("Student");
    this.applyGrid.addColumn(Apply::getStudentName).setHeader(headerStudent).setWidth(COLWIDTH);
    H4 headerAd = new H4("Jobanzeige");
    this.applyGrid.addColumn(Apply::getAdvertisementTitle).setHeader(headerAd).setWidth(COLWIDTH);
    H4 headerApplied = new H4("Beworben am");
    this.applyGrid.addColumn(Apply::getFormattedDate).setHeader(headerApplied).setWidth(COLWIDTH);
    this.applyGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
    this.applyGrid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
    this.applyGrid.addThemeVariants(GridVariant.MATERIAL_COLUMN_DIVIDERS);
    this.applyGrid.addSelectionListener(selectionEvent -> {
      Optional<Apply> optionalApply = selectionEvent.getFirstSelectedItem();
      optionalApply.ifPresent(
          apply -> UI.getCurrent().navigate("deine-bewerbung/" + apply.getId()));
    });
    div.add(this.applyGrid);
    refreshTableData();
    return div;
  }

  private void refreshTableData() {
    this.applications.clear();
    if (this.showall) {
      this.applications.addAll(applyService.findAll());
      this.applyGrid.getDataProvider().refreshAll();
      return;
    }
    if (currentUser != null) {
      this.applications.addAll(applyService.findApplicationsByUserID(currentUser.getId()));
      this.applyGrid.getDataProvider().refreshAll();
    }


  }

  public void showAll() {
    this.showall = true;
    refreshTableData();
  }
}
