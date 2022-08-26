package org.hbrs.academicflow.view.common.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.router.RouterLink;

public class TabGenerator {

  private TabGenerator() {
    // private constructor to hide implicit public one
  }

  public static Tab buildTab(String text, Class<? extends Component> navigationTarget) {
    final Tab tab = new Tab();
    tab.add(new RouterLink(text, navigationTarget));
    ComponentUtil.setData(tab, Class.class, navigationTarget);
    return tab;
  }
}
