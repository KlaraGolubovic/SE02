package org.hbrs.academicflow.view.common.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import org.hbrs.academicflow.model.advertisement.AdvertisementDTO;
import org.hbrs.academicflow.util.Constants;

public class LabeledButtonCollection {

  private static final int BUTTON_WIDTH_PER_CHAR = 8;

  private LabeledButtonCollection() {
  }

  public static Button generalButton(String text, String target) {
    Button button = new Button(text);
    button.addClickListener(
        clickEvent -> {
          UI ui = UI.getCurrent();
          ui.getPage().getHistory().pushState(null, ui.getInternals().getActiveViewLocation());
          // make sure this is the correct path
          ui.navigate(target);
        });
    return button;
  }

  public static Button defaultButton(String text) {
    Button button = new Button(text);
    button.setWidth(widthString(text.length()));
    return button;
  }

  private static String widthString(int length) {
    // calculate width of button from text length
    int width = length * BUTTON_WIDTH_PER_CHAR;
    return "" + width / 10 + "." + width % 10 + "em";
  }

  public static Component actionCompanyButton(AdvertisementDTO advertisementDto) {
    return LabeledButtonCollection.generalButton(
        "Profil von " + advertisementDto.getCompany().getName() + "  ",
        Constants.Pages.ORGANIZATION_PROFILE_STUDENT_VIEW
            + "/"
            + advertisementDto.getCompany().getId());
  }

  public static Component actionCompanyLabel(AdvertisementDTO advertisementDto) {
    return new Html(
        "<a href= \""
            + Constants.Pages.ORGANIZATION_PROFILE_STUDENT_VIEW
            + "/"
            + advertisementDto.getCompany().getId()
            + "\" style=\"color:var(--branding-dark-blue);\">"
            + advertisementDto.getCompany().getName()
            + "</a> ");
  }

  public static Button registerButton(String text) {
    Button button = new Button(text);
    button.addClickListener(
        clickEvent -> {
          UI ui = UI.getCurrent();
          ui.getPage().getHistory().pushState(null, "#");
          ui.navigate(Constants.Pages.REGISTER_VIEW);
        });
    return button;
  }

  public static Button loginButton(String text) {
    Button button = new Button(text);
    button.addClickListener(
        clickEvent -> {
          UI ui = UI.getCurrent();
          ui.getPage().getHistory().pushState(null, "#");
          ui.navigate(Constants.Pages.LOGIN_VIEW);
        });
    return button;
  }
}
