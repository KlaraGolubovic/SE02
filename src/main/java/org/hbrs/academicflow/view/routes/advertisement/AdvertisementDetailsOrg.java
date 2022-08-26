package org.hbrs.academicflow.view.routes.advertisement;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

public class AdvertisementDetailsOrg extends Div {

  protected final Select<String> jobTypeField = new Select<>();
  protected final TextField titleField = new TextField("Titel");
  protected final TextArea descriptionField = new TextArea("Beschreibung");
  protected final Checkbox isRemoteCheckBox = new Checkbox("Remote");
  protected final Image image = new Image("images/corporation-profile.png", "Unternehmensbild");
}
