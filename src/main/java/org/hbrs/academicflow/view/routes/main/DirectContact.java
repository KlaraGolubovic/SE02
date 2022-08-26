package org.hbrs.academicflow.view.routes.main;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.hbrs.academicflow.view.common.components.VerticalSpacerGenerator;

public class DirectContact extends Div {

  public DirectContact() {
    VerticalLayout layout = new VerticalLayout();
    layout.setWidth("90%");
    H2 heading = new H2("Hilfe für Flüchtlinge");
    H2 headingE = new H2("Help for refugees");
    layout.add(heading, headingE);
    layout.getStyle().set("border-top", "6px solid #0057B8");
    layout.getStyle().set("border-left", "6px solid #0057B8");
    layout.getStyle().set("border-right", "6px solid #FFD700");
    layout.getStyle().set("border-bottom", "6px solid #FFD700");
    Html emailLink =
        new Html(
            "<a href= \"mailto:Contact@AcademicFlow.de\" style=\"color:blue;\">довідкова пошта"
                + " (Contact@AcademicFlow.de)</a> ");
    layout.add(
        new Paragraph(
            "Ласкаво просимо на сайт \"Академічний потік\"! Пропонуємо вам портал вакансій для"
                + " студентів та компаній! \n"));
    layout.add(
        new Paragraph(
            "В даний час наш веб-сайт доступний лише німецькою мовою. Тим не менш, ми будемо раді"
                + " допомогти вам під "));
    layout.add(emailLink);
    layout.add(new Paragraph("і пояснити вам наш портал вакансій."));
    this.add(new VerticalSpacerGenerator("2em").buildVerticalSpacer());
    this.add(layout);
  }
}
