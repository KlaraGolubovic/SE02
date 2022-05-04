package org.hbrs.academicflow.view.components;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VerticalSpacerGenerator {
  @Autowired private final String height;


  public Div newVerticalSpacer() {
    Div div = new Div();
    div.addClassName("vertical-spacer");
    div.setHeight(this.height);
    return div;
  }
}
