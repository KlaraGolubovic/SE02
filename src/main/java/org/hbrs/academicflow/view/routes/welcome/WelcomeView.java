package org.hbrs.academicflow.view.routes.welcome;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.hbrs.academicflow.util.Constants;
import org.hbrs.academicflow.util.SessionAttributes;
import org.hbrs.academicflow.util.Statistics;
import org.hbrs.academicflow.view.common.layouts.AppView;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Route(value = Constants.Pages.WELCOME_VIEW, layout = AppView.class)
@PageTitle("Startseite")
@CssImport("./styles/views/main/main-view.css")
@CssImport("./styles/views/main/welcome.css")
public class WelcomeView extends Div {

  private final transient Statistics stats;
  private final Image image = new Image("images/WillkommenFoto.jpg", "WillkommenFoto");
  int charLimit = 140;

  @PostConstruct
  public void doInitialSetup() {
    VerticalLayout layoutV = new VerticalLayout();
    layoutV.add(new H2(
        "Willkommen bei Academic Flow, " + SessionAttributes.getCurrentUser().getUsername()));
    HorizontalLayout layoutH = new HorizontalLayout();
    layoutH.setWidth("80%");
    this.image.addClassName("profile-picture");
    this.image.setWidth("15vw");
    layoutH.add(new H2(""));
    layoutH.add(this.image);
    Paragraph beschreibung = new Paragraph(
        "Wilkommen auf AcadamicFlow. " + "Unser Ziel ist es, DIE Jobbörse für Studenten zu werden. "
            + "Wir haben viele spannende Stellenanzeigen von den Top - Unternehmen. "
            + "Wir bieten außerdem die Möglichkeit, nach Tätigkeitsfeldern wie Informatik "
            + "oder Maschinenbau zu suchen. Da ist sicher auch für dich etwas dabei."
            + " Leg jetzt los und finde deinen Traumjob!");
    beschreibung.getStyle().set("font-size", "var(--lumo-font-size-l)");
    layoutH.add(beschreibung);
    // Abstände verkleinern
    layoutV.add(layoutH);
    add(layoutV);
    HorizontalLayout hl = new HorizontalLayout();
    VerticalLayout vl1 = new VerticalLayout();
    H1 a = new H1(getamountStellenanzeigen());
    vl1.setAlignItems(Alignment.CENTER);
    vl1.add(new Label("Anzahl Stellenanzeigen"));
    vl1.add(a);
    hl.add(vl1);
    VerticalLayout vl2 = new VerticalLayout();
    H1 b = new H1(getamountUnternehmen());
    vl2.setAlignItems(Alignment.CENTER);
    vl2.add(new Label("Registrierte Unternehmen"));
    vl2.add(b);
    vl2.setMargin(false);
    hl.add(vl2);
    VerticalLayout vl3 = new VerticalLayout();
    H1 c = new H1(getamountBewerbungen());
    vl3.setAlignItems(Alignment.CENTER);
    vl3.add(new Label("Eingegangene Bewerbungen"));
    vl3.add(c);
    hl.add(vl3);
    VerticalLayout vl4 = new VerticalLayout();
    H1 d = new H1(getamountStudenten());
    vl4.setAlignItems(Alignment.CENTER);
    vl4.add(new Label("Registrierte Studenten"));
    vl4.add(d);
    hl.add(vl4);
    vl1.addClassName("card");
    vl2.addClassName("card");
    vl3.addClassName("card");
    vl4.addClassName("card");
    this.add(hl);
  }

  private String getamountStellenanzeigen() {
    return "" + stats.getAmountOfJobAds();
  }

  /**
   * Diese Methode verwendet den Service für die Studnetenprofile.
   *
   * @return Menge an Studenten im System als String
   */
  private String getamountStudenten() {
    return "" + stats.getAmountOfStudents();
  }

  private String getamountUnternehmen() {
    return "" + stats.getAmountOfCompanies();
  }

  private String getamountBewerbungen() {
    return "" + stats.getAmountOfApplies();
  }
}
