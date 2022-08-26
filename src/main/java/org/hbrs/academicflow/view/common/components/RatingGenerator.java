package org.hbrs.academicflow.view.common.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import java.text.DecimalFormat;

public class RatingGenerator extends Div {

  private static final String RATING_STAR = "rating star";
  private static final String HEIGHT = "height";
  private final Image[] unselected = new Image[5];
  private final Image[] selected = new Image[5];
  private int currentRating;

  public int getCurrentRatingIndex() {
    return currentRating;
  }

  public Component getRatingButton() {
    return this.createRatingButton();
  }

  public Component getRatingDisplay(double ratedIndex) {
    return this.createRatingDisplay(ratedIndex);
  }

  private Component createRatingDisplay(double ratedD) {
    int rated = (int) Math.floor(ratedD + 0.5);
    DecimalFormat df = new DecimalFormat("0.00");
    HorizontalLayout layout = new HorizontalLayout();
    for (int i = 0; i < rated; i++) {
      Image s = new Image("images/selected.png", RATING_STAR);
      s.getStyle().set(HEIGHT, "4vh");
      layout.add(s);
    }
    for (int i = rated; i < 5; i++) {
      Image u = new Image("images/unselected.png", RATING_STAR);
      u.getStyle().set(HEIGHT, "4vh");
      layout.add(u);
    }
    layout.add(new H3("(" + df.format(ratedD) + ")"));
    layout.setAlignItems(Alignment.CENTER);
    return layout;
  }

  private Component createRatingButton() {
    HorizontalLayout layout = new HorizontalLayout();
    for (int i = 0; i < selected.length; i++) {
      this.selected[i] = new Image("images/selected.png", RATING_STAR);
      this.selected[i].getStyle().set(HEIGHT, "8vh");
      final int a = i;
      this.selected[i].addClickListener(e -> ratedIndex(a));
      layout.add(this.selected[i]);
    }
    for (int i = 0; i < unselected.length; i++) {
      this.unselected[i] = new Image("images/unselected.png", RATING_STAR);
      this.unselected[i].getStyle().set(HEIGHT, "8vh");
      final int a = i;
      this.unselected[i].addClickListener(e -> ratedIndex(a));
      layout.add(this.unselected[i]);
    }
    // get number - assuming 1
    ratedIndex(0);
    return layout;
  }

  private void ratedIndex(int clickedButton) {
    this.currentRating = clickedButton;
    for (int i = 0; i < unselected.length; i++) {
      if (i <= clickedButton) {
        this.selected[i].setVisible(true);
        this.unselected[i].setVisible(false);
      } else {
        this.unselected[i].setVisible(true);
        this.selected[i].setVisible(false);
      }
    }
  }
}