package org.hbrs.academicflow.view.routes.advertisement;

import com.google.common.collect.Lists;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.hbrs.academicflow.control.apply.ApplyService;
import org.hbrs.academicflow.model.apply.Apply;
import org.hbrs.academicflow.util.Constants.Pages;
import org.hbrs.academicflow.util.SessionAttributes;
import org.hbrs.academicflow.view.common.layouts.AppView;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Route(value = Pages.STUDENT_APPLICATION_VIEW, layout = AppView.class)
@PageTitle("Meine Bewerbungen")
public class StudentApplicationView extends Div {

  private final transient ApplyService applyService;
  private final List<Apply> applications = Lists.newCopyOnWriteArrayList();

  @PostConstruct
  private void init() {
    Div div = new Div();
    VerticalLayout layout = new VerticalLayout();
    final UUID studentID = SessionAttributes.getCurrentUser().getId();
    applications.addAll(applyService.findApplicationByStudent(studentID));
    layout.add(new H2("Deine Bewerbungen im Überblick"));
    if (!applications.isEmpty()) {
      layout.add(buildGrid());
    } else {
      Notification.show("Es liegen keine Bewerbungen vor.");
      Span span1 = new Span();
      Span span2 = new Span();
      String text1 = "Du scheinst noch keine Bewerbungen verschickt zu haben.";
      String text2 = "Wage jetzt den ersten Schritt!";
      span1.add(new Paragraph(text1));
      span2.add(new Paragraph(text2));
      layout.add(span1);
      layout.add(span2);
    }
    div.add(layout);
    add(div);
    applyService.close();
  }

  private Component buildGrid() {
    final Grid<Apply> grid = new Grid<>();
    grid.setDataProvider(new ListDataProvider<>(applications));
    H4 headerDate = new H4("Datum der Bewerbung");
    grid.addColumn(Apply::getFormattedDate).setHeader(headerDate).setAutoWidth(true);
    H4 headerAd = new H4("Anzeige");
    grid.addColumn(Apply::getAdvertisementTitle).setHeader(headerAd).setAutoWidth(true);
    H4 headerComp = new H4("Unternehmen");
    grid.addColumn(Apply::getCompanyName).setHeader(headerComp).setAutoWidth(true);
    grid.addThemeVariants();
    grid.addSelectionListener(
        selectionEvent -> {
          Optional<Apply> optionalApply = selectionEvent.getFirstSelectedItem();
          optionalApply.ifPresent(
              apply -> UI.getCurrent().navigate("deine-bewerbung/" + apply.getId()));
        });
    return grid;
  }
}
