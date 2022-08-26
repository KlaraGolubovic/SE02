package org.hbrs.academicflow.view.routes.advertisement.component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.RequiredArgsConstructor;
import org.hbrs.academicflow.control.advertisement.AdvertisementMapperImpl;
import org.hbrs.academicflow.model.advertisement.Advertisement;
import org.hbrs.academicflow.model.advertisement.AdvertisementDTO;
import org.hbrs.academicflow.view.common.components.LabeledButtonCollection;

@UIScope
@SpringComponent
@RequiredArgsConstructor
@SuppressWarnings("java:S110")
public class EditableAdvertisement extends VisibleAd {

  public EditableAdvertisement(Advertisement advertisement) {
    this(new AdvertisementMapperImpl().toDTO(advertisement));
  }

  public EditableAdvertisement(AdvertisementDTO advertisement) {
    super(advertisement);
  }

  @Override
  protected Button actionButtonForAd(AdvertisementDTO advertisement) {
    return LabeledButtonCollection.generalButton("Bearbeiten  ", "editad/" + advertisement.getId());
  }
}
