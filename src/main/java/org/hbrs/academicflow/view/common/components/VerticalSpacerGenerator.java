package org.hbrs.academicflow.view.common.components;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@SpringComponent
@UIScope
@RequiredArgsConstructor
public class VerticalSpacerGenerator {

  private final String height;

  public @NotNull Div buildVerticalSpacer() {
    final Div div = new Div();
    div.addClassName("vertical-spacer");
    Label emptyLabel = new Label(" ");
    emptyLabel.setHeight(this.height);
    VerticalLayout vl = new VerticalLayout();
    vl.add(emptyLabel);
    div.add(vl);
    return div;
  }
}
