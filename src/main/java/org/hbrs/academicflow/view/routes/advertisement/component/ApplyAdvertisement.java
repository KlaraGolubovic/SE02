package org.hbrs.academicflow.view.routes.advertisement.component;

import static org.hbrs.academicflow.view.common.components.LabeledButtonCollection.actionCompanyButton;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.time.Instant;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hbrs.academicflow.control.advertisement.AdvertisementService;
import org.hbrs.academicflow.control.apply.ApplyService;
import org.hbrs.academicflow.control.student.StudentService;
import org.hbrs.academicflow.model.advertisement.Advertisement;
import org.hbrs.academicflow.model.advertisement.AdvertisementDTO;
import org.hbrs.academicflow.model.apply.Apply;
import org.hbrs.academicflow.model.apply.ApplyDTO;
import org.hbrs.academicflow.model.student.user.Student;
import org.hbrs.academicflow.util.Constants;
import org.hbrs.academicflow.util.Constants.Pages;
import org.hbrs.academicflow.util.SessionAttributes;
import org.hbrs.academicflow.view.common.layouts.AppView;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
@PageTitle("Jetzt Bewerben")
@Slf4j
@Route(value = Constants.Pages.STUDENT_APPLY_AD, layout = AppView.class)
@CssImport("./styles/views/main/main-view.css")
@CssImport("./styles/views/main/advertisements.css")
public class ApplyAdvertisement extends Div implements HasUrlParameter<String> {

  private final transient StudentService studentService;
  private final transient ApplyService applyService;
  private final transient AdvertisementService advertisementService;
  private final Button applyButton = new Button("Bewerben");
  private final TextArea note = new TextArea();
  private UUID advertisementId = null;
  private transient Student currentStudent;
  private transient Apply alreadyapplied;

  private void init() {
    AdvertisementDTO advertisement = advertisementService.findAdvertisementById(advertisementId);
    if (advertisement == null) {
      log.error("Advertisement not found, requested UID: {}", advertisementId);
      getUI().ifPresent(ui -> ui.navigate(Pages.STUDENT_LIST_ADS));
      Notification.show("Anzeige nicht gefunden", 9000, Position.TOP_STRETCH);
      return;
    }
    log.info("Advertisement found, with Active: {}", advertisement.getActive());
    addClassName("search-advertisements-view");
    VerticalLayout layout = new VerticalLayout();
    layout.setWidth("100%");
    layout.setAlignItems(Alignment.CENTER);
    layout.add(actionCompanyButton(advertisement));
    layout.add(new Span("Anzeige:\n" + advertisement.getTitle()));
    layout.add(new Span("Beschreibung:\n" + advertisement.getDescription()));
    layout.add(new Span("Job-Typ:\n" + advertisement.getJobType()));
    final int charLimit = 1000;
    note.setWidthFull();
    note.setLabel("Nachricht");
    note.setMaxLength(charLimit);
    note.setMinLength(20);
    note.setValueChangeMode(ValueChangeMode.EAGER);
    note.addValueChangeListener(
        e -> e.getSource().setHelperText(e.getValue().length() + "/" + charLimit));
    Boolean isStudent = SessionAttributes.isCurrentUserStudent(studentService);
    if (isStudent != null && isStudent.booleanValue()) {
      layout.add(note);
      applyButton.addClickListener(event -> this.apply());
      layout.add(applyButton);
      currentStudent = studentService.findStudentByUserID(
          SessionAttributes.getCurrentUser().getId());
      alreadyapplied = applyService.findByStudentAndAdvertisement(currentStudent.getId(),
          advertisement.getId());
      if (alreadyapplied != null) {
        applyButton.setEnabled(false);
        applyButton.setText("Bewerbung bereits gesendet");
        note.setValue(alreadyapplied.getNote());
        note.setEnabled(false);
      }

    }
    HorizontalLayout hLayout = new HorizontalLayout();
    hLayout.setWidthFull();
    hLayout.setSpacing(false);
    hLayout.setAlignItems(FlexComponent.Alignment.CENTER);
    hLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.EVENLY);
    hLayout.add(layout);
    this.add(hLayout);
    add(layout);
  }

  private void apply() {
    if (note.getValue().length() < 20) {
      Notification zukurz = Notification.show("Nachricht zu kurz (min 20 Zeichen)", 9000,
          Position.BOTTOM_CENTER);
      zukurz.addThemeVariants(NotificationVariant.LUMO_PRIMARY);
      zukurz.addThemeVariants(NotificationVariant.LUMO_ERROR);
      return;
    }
    ApplyDTO applydto = new ApplyDTO();
    Advertisement advertisement = advertisementService.findFullAdvertisementById(advertisementId);
    applydto.setAdvertisement(advertisement);

    applydto.setStudent(currentStudent);
    applydto.setApplied(Instant.now());
    applydto.setNote(note.getValue());
    if (alreadyapplied != null) {
      Notification bereitsBeworben = Notification.show(
          "Du hast dich bereits auf diese Jobanzeige beworben!", 9000, Position.BOTTOM_CENTER);
      bereitsBeworben.addThemeVariants(NotificationVariant.LUMO_PRIMARY);
      bereitsBeworben.addThemeVariants(NotificationVariant.LUMO_ERROR);
    } else {
      applyService.doSaveApplication(applydto.buildApplyfromDTO());
      Notification.show("Deine Bewerbung war erfolgreich!");
    }
    note.clear();
    applyService.close();
    getUI().ifPresent(ui -> ui.navigate(Pages.STUDENT_APPLICATION_VIEW));
  }

  @Override
  public void setParameter(BeforeEvent event, String parameter) {
    if (parameter == null) {
      advertisementId = null;
    } else {
      advertisementId = UUID.fromString(parameter);
      this.init();
    }
  }
}
