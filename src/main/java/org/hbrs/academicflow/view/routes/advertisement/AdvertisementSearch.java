package org.hbrs.academicflow.view.routes.advertisement;

import com.google.common.collect.Lists;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.hbrs.academicflow.control.advertisement.SearchProxy;
import org.hbrs.academicflow.control.advertisement.Suchanfrage;
import org.hbrs.academicflow.model.advertisement.Advertisement;
import org.hbrs.academicflow.util.Constants;
import org.hbrs.academicflow.view.common.components.VerticalSpacerGenerator;
import org.hbrs.academicflow.view.common.layouts.AppView;
import org.hbrs.academicflow.view.routes.advertisement.component.VisibleAd;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Route(value = Constants.Pages.STUDENT_LIST_ADS, layout = AppView.class)
@PageTitle("Alle Jobanzeigen")
@CssImport("./styles/views/main/main-view.css")
@CssImport("./styles/views/main/advertisements.css")
public class AdvertisementSearch extends Div {

  private final transient SearchProxy searchProxy;
  private final transient Suchanfrage suchanfrage = new Suchanfrage();
  private final List<Advertisement> advertisements = Lists.newCopyOnWriteArrayList();
  VerticalLayout layout;

  @PostConstruct
  public void init() {
    addClassName("search-advertisements-view");
    FormLayout header = new FormLayout();
    header.add(this.filterRemoteButton());
    header.add(this.filterRemoteIrellevantButton());
    header.add(this.filterNotRemoteButton());
    this.add(new VerticalSpacerGenerator("1em").buildVerticalSpacer());
    this.add(header);
    this.add(this.searchBar());
    this.advertisements.clear();
    this.advertisements.addAll(searchProxy.suche(this.suchanfrage));
    layout = new VerticalLayout();
    this.add(this.buildAdvertisementList());
  }

  private FormLayout searchBar() {
    FormLayout searchBar = new FormLayout();
    searchBar.addClassName("search-bar");
    TextField searchTerm = new TextField("Suche");
    searchTerm.setMaxLength(255);
    searchBar.add(searchTerm);
    searchTerm.setPreventInvalidInput(true);
    Button search = new Button("suchen");
    search.addClickListener(
        event -> {
          this.suchanfrage.setFilterString(
              advertisement ->
                  (advertisement
                          .getTitle()
                          .toLowerCase()
                          .contains(searchTerm.getValue().toLowerCase())
                      || advertisement
                          .getDescription()
                          .toLowerCase()
                          .contains(searchTerm.getValue().toLowerCase())));
          fill(layout);
        });
    searchBar.add(search);
    return searchBar;
  }

  public Component buildAdvertisementList() {
    if (layout == null) {
      layout = new VerticalLayout();
    }
    layout.setSpacing(false);
    layout.setPadding(false);
    layout.setClassName("advertisements-list");
    fill(layout);
    return layout;
  }

  private Button filterNotRemoteButton() {
    Button button = new Button("Nur Präsenz");
    button.addClickListener(this::filterNotRemoteAction);
    return button;
  }

  private void filterNotRemoteAction(ClickEvent<Button> buttonClickEvent) {

    this.suchanfrage.setFilterRemote(advertisement -> !advertisement.getRemote());
    fill(layout);
  }

  private Button filterRemoteIrellevantButton() {
    Button button = new Button("Remote und Präsenz");
    button.addClickListener(
        event -> {
          this.suchanfrage.setFilterRemote(advertisement -> true);

          fill(layout);
        });
    return button;
  }

  private Button filterRemoteButton() {
    Button filterRemote = new Button("Nur Remoteangebote");
    filterRemote.addClickListener(
        event -> {
          this.suchanfrage.setFilterRemote(Advertisement::getRemote);

          fill(layout);
        });
    return filterRemote;
  }

  private void fill(VerticalLayout layout) {
    layout.removeAll();
    this.advertisements.clear();
    this.advertisements.addAll(searchProxy.suche(this.suchanfrage));
    for (Advertisement advertisement : this.advertisements) {
      layout.add(new VisibleAd(advertisement));
    }
  }
}