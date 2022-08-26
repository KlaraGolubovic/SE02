package org.hbrs.academicflow.view.routes.advertisement.component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.RequiredArgsConstructor;
import org.hbrs.academicflow.control.advertisement.AdvertisementMapperImpl;
import org.hbrs.academicflow.model.advertisement.Advertisement;
import org.hbrs.academicflow.model.advertisement.AdvertisementDTO;
import org.hbrs.academicflow.view.common.components.LabeledButtonCollection;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class VisibleAd extends Div {

  public VisibleAd(@NotNull AdvertisementDTO advertisement) {
    this.addClassName("outer");
    VerticalLayout advertisementDiv = new VerticalLayout();
    advertisementDiv.setWidth("100%");
    this.setWidth("100%");
    advertisementDiv.add(new Span(new H2(advertisement.getTitle())));
    HorizontalLayout companyLine = new HorizontalLayout();
    companyLine.add(new Span("Unternehmen: "));
    companyLine.add(LabeledButtonCollection.actionCompanyLabel(advertisement));
    advertisementDiv.add(companyLine);
    String remString = Boolean.TRUE.equals(advertisement.getRemote()) ? "Ja" : "Nein";
    advertisementDiv.add(new Span("Job-Typ: " + advertisement.getJobType()));
    advertisementDiv.add(new Span(" Remote: " + remString));
    Details details = new Details();
    details.setOpened(false);
    details.setSummaryText("Beschreibung anzeigen");
    details.addContent(new Paragraph(advertisement.getDescription()));
    advertisementDiv.add(details);
    HorizontalLayout hLayout = new HorizontalLayout();
    hLayout.setWidthFull();
    hLayout.setSpacing(false);
    hLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.START);
    hLayout.add(advertisementDiv);
    // Left Side of Ad
    VerticalLayout buttonLayout = new VerticalLayout();
    buttonLayout.add(new Span(""));
    buttonLayout.setWidth("40%");
    Image image = new Image("images/corporation-profile.png", "Unternehmensbild");
    image.addClassName("profile-picture");
    image.setWidth("100%");
    buttonLayout.add(image);
    buttonLayout.add(this.actionButtonForAd(advertisement));
    buttonLayout.add(LabeledButtonCollection.actionCompanyButton(advertisement));
    buttonLayout.setAlignItems(Alignment.STRETCH);
    hLayout.add(buttonLayout);
    this.add(hLayout);
  }

  public VisibleAd(Advertisement advertisement) {
    this(new AdvertisementMapperImpl().toDTO(advertisement));
  }

  protected Button actionButtonForAd(AdvertisementDTO advertisement) {
    return LabeledButtonCollection.generalButton(
        "Anzeige ansehen", "apply/" + advertisement.getId());
  }
}
