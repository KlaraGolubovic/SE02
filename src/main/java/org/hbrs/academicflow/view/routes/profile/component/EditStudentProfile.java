package org.hbrs.academicflow.view.routes.profile.component;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.UploadI18N;
import com.vaadin.flow.component.upload.UploadI18N.AddFiles;
import com.vaadin.flow.component.upload.UploadI18N.DropFiles;
import com.vaadin.flow.component.upload.UploadI18N.Error;
import com.vaadin.flow.component.upload.UploadI18N.Units;
import com.vaadin.flow.component.upload.UploadI18N.Uploading;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.hbrs.academicflow.control.location.LocationMapper;
import org.hbrs.academicflow.control.location.LocationService;
import org.hbrs.academicflow.control.student.StudentService;
import org.hbrs.academicflow.control.student.profile.StudentProfileService;
import org.hbrs.academicflow.model.location.Location;
import org.hbrs.academicflow.model.location.LocationDTO;
import org.hbrs.academicflow.model.student.profile.StudentProfile;
import org.hbrs.academicflow.model.student.user.Student;
import org.hbrs.academicflow.model.user.UserDTO;
import org.hbrs.academicflow.util.Constants;
import org.hbrs.academicflow.util.SessionAttributes;
import org.hbrs.academicflow.view.common.components.VerticalSpacerGenerator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class EditStudentProfile extends Div {

  private final transient LocationMapper locationMapper;
  private final transient StudentService studentService;
  private final transient LocationService locationService;
  private final transient StudentProfileService profileService;
  private final Button saveButton = new Button("Profil speichern");
  private final Button resetButton = new Button("Zurücksetzen");
  private final TextField vorname = new TextField("Vorname");
  private final TextField nachname = new TextField("Nachname");
  private final TextField cityField = new TextField("Stadt");
  private final TextField zipCodeField = new TextField("PLZ");
  private final TextField countryField = new TextField("Land");
  private final TextField streetField = new TextField("Straße");
  private final TextArea descriptionField = new TextArea("Beschreibung");
  private final TextField houseNumberField = new TextField("Hausnummer");
  private final Image image = new Image("images/studentP.png", "Profilbild");
  private final AtomicBoolean newlyGenerated = new AtomicBoolean();
  Button uploadButton = new Button("PDF Hochladen...");
  Select<String> select = new Select<>();
  private transient UserDTO loginUser;
  private transient StudentProfile profile;

  @PostConstruct
  void init() {
    if (!SessionAttributes.isCurrentUserStudent(studentService)) {
      return;
    }
    MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
    Upload upload = new Upload(buffer);
    this.add(new VerticalSpacerGenerator("1em").buildVerticalSpacer());
    addClassName("show-users-view");
    add(this.image);
    HorizontalLayout hLayout = new HorizontalLayout();
    hLayout.setWidthFull();
    hLayout.setSpacing(false);
    hLayout.setAlignItems(FlexComponent.Alignment.CENTER);
    hLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.EVENLY);
    hLayout.add(this.image);
    this.image.setWidth("10vw");
    select.setLabel("Geschlecht");
    select.setItems("Mann", "Frau", "Divers", "Keine Angaben");
    select.setValue("Most recent first");
    upload.setAcceptedFileTypes("application/pdf", ".pdf");
    upload.setUploadButton(uploadButton);
    upload
        .getElement()
        .addEventListener(
            "max-files-reached-changed",
            event -> {
              boolean maxFilesReached = event.getEventData().getBoolean("event.detail.value");
              uploadButton.setEnabled(!maxFilesReached);
            })
        .addEventData("event.detail.value");
    upload.setI18n(uploadI18N());
    add(hLayout);
    try {
      this.loginUser = SessionAttributes.getCurrentUser();
      if (this.loginUser == null) {
        UI.getCurrent().navigate(Constants.Pages.LOGIN_VIEW);
        return;
      }
    } catch (NullPointerException e) {
      return;
    }
    this.profile = this.profileService.findStudentProfileByUser(this.loginUser);
    if (this.profile == null) {
      this.newlyGenerated.set(true);
      this.profile = this.buildEmptyCompanyProfile();
    }
    this.fillFieldsWithProfileInformation();
    this.resetButton.addClickListener(event -> this.fillFieldsWithProfileInformation());
    this.saveButton.addClickListener(event -> this.saveAndExit());
    // Start: Build components
    final FormLayout formLayout = new FormLayout();
    Paragraph hint =
        new Paragraph("Lade hier deine Dokumente (z. B. Lebenslauf, Nachweise...) hoch:");
    hint.getStyle().set("color", "var(--lumo-secondary-text-color)");
    hint.getStyle().set("padding-bottom", "1em");
    upload.getStyle().set("margin-bottom", "2em");
    formLayout.add(
        this.vorname,
        this.nachname,
        this.streetField,
        this.houseNumberField,
        this.zipCodeField,
        this.cityField,
        this.countryField,
        this.select,
        this.descriptionField,
        hint,
        upload,
        this.saveButton,
        this.resetButton);
    saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    formLayout.setColspan(this.streetField, 1);
    formLayout.setColspan(this.zipCodeField, 1);
    formLayout.setColspan(this.houseNumberField, 1);
    formLayout.setColspan(this.cityField, 1);
    formLayout.setColspan(this.countryField, 1);
    formLayout.setColspan(this.resetButton, 1);
    formLayout.setColspan(this.saveButton, 1);
    formLayout.setColspan(this.resetButton, 1);
    formLayout.setColspan(this.vorname, 1);
    formLayout.setColspan(this.nachname, 1);
    formLayout.setColspan(upload, 2);
    formLayout.setColspan(this.descriptionField, 2);
    formLayout.setWidthFull();
    this.add(formLayout);
    this.add(new VerticalSpacerGenerator("4vh").buildVerticalSpacer());
    // End: Build components
  }

  UploadI18N uploadI18N() {
    UploadI18N i18N = new UploadI18N();
    i18N.setDropFiles(
        new DropFiles()
            .setOne("Lade hier deinen Lebenslauf hoch")
            .setMany("Lade hier deinen Lebenslauf hoch"));
    i18N.setAddFiles(new AddFiles().setOne("Upload File...").setMany("Upload Files..."));
    i18N.setCancel("Cancel");
    i18N.setError(
        new Error()
            .setTooManyFiles("Too Many Files.")
            .setFileIsTooBig("File is Too Big.")
            .setIncorrectFileType("Incorrect File Type."));
    i18N.setUploading(
        new Uploading()
            .setStatus(
                new Uploading.Status()
                    .setConnecting("Connecting...")
                    .setStalled("Stalled")
                    .setProcessing("Processing File...")
                    .setHeld("Queued"))
            .setRemainingTime(
                new Uploading.RemainingTime()
                    .setPrefix("remaining time: ")
                    .setUnknown("unknown remaining time"))
            .setError(
                new Uploading.Error()
                    .setServerUnavailable("Upload failed, please try again later")
                    .setUnexpectedServerError("Upload failed due to server error")
                    .setForbidden("Upload forbidden")));
    i18N.setUnits(
        new Units().setSize(Arrays.asList("B", "kB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB")));
    return i18N;
  }

  private void fillFieldsWithProfileInformation() {
    if (this.profile == null) {
      Student s = this.studentService.findStudentByUserID(this.loginUser.getId());
      this.vorname.setValue(s.getFirstName());
      this.nachname.setValue(s.getLastName());
      return;
    }
    this.descriptionField.setValue(this.profile.getDescription());
    this.countryField.setValue(this.profile.getLocation().getCountry());
    this.vorname.setValue(this.profile.getStudent().getFirstName());
    this.nachname.setValue(this.profile.getStudent().getLastName());
    this.cityField.setValue(this.profile.getLocation().getCity());
    this.houseNumberField.setValue(this.profile.getLocation().getHouseNumber());
    this.streetField.setValue(this.profile.getLocation().getStreet());
    this.zipCodeField.setValue(this.profile.getLocation().getZipCode());
  }

  private void saveAndExit() {
    final Student student = this.studentService.findStudentByUserID(this.loginUser.getId());
    if (student == null) {
      return;
    }
    student.setFirstName(this.vorname.getValue());
    student.setLastName(this.nachname.getValue());
    this.studentService.doUpdateStudent(student);
    final LocationDTO locationDTO = this.toLocationDTO();
    if (this.newlyGenerated.get()) {
      final Location location =
          this.locationService.save(this.locationMapper.toEntity(locationDTO));
      final StudentProfile studentProfil =
          StudentProfile.builder()
              .student(student)
              .description(this.descriptionField.getValue())
              .image(0)
              .location(location)
              .build();
      this.profileService.updateStudentProfile(studentProfil);
      Notification.show("Das Profil wurde erstellt");
    } else {
      final Location location =
          this.locationMapper.update(
              locationDTO, this.profile.getLocation().getId(), this.profile.getLocation());
      this.profile.setDescription(this.descriptionField.getValue());
      this.profile.setLocation(this.locationService.save(location));
      this.profileService.updateStudentProfile(this.profile);
      Notification.show("Das Profil wurde aktualisiert");
    }
  }

  private @NotNull LocationDTO toLocationDTO() {
    return new LocationDTO(
        this.streetField.getValue(),
        this.houseNumberField.getValue(),
        this.cityField.getValue(),
        this.zipCodeField.getValue(),
        this.countryField.getValue());
  }

  private @Nullable StudentProfile buildEmptyCompanyProfile() {
    final Student student = this.studentService.findStudentByUserID(this.loginUser.getId());
    if (student == null) {
      return null;
    }
    final Location location =
        Location.builder().zipCode("").street("").houseNumber("").country("").city("").build();
    return StudentProfile.builder()
        .location(location)
        .student(student)
        .description("")
        .image(0)
        .build();
  }
}
