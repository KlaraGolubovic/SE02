package org.hbrs.academicflow.test.common;

import com.google.common.collect.Lists;
import java.time.Instant;
import java.util.HashSet;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hbrs.academicflow.model.advertisement.Advertisement;
import org.hbrs.academicflow.model.apply.Apply;
import org.hbrs.academicflow.model.company.profile.CompanyProfile;
import org.hbrs.academicflow.model.company.user.Company;
import org.hbrs.academicflow.model.location.Location;
import org.hbrs.academicflow.model.permission.PermissionGroup;
import org.hbrs.academicflow.model.student.profile.StudentProfile;
import org.hbrs.academicflow.model.student.user.Student;
import org.hbrs.academicflow.model.user.User;
import org.hbrs.academicflow.util.Encryption;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DefaultValues {

  public static final User DEFAULT_USER;
  public static final Student DEFAULT_STUDENT;
  public static final Company DEFAULT_COMPANY;
  public static final Location DEFAULT_LOCATION;
  public static final Advertisement DEFAULT_ADVERTISEMENT;
  public static final Apply DEFAULT_APPLY;
  public static final StudentProfile DEFAULT_STUDENT_PROFILE;
  public static final CompanyProfile DEFAULT_COMPANY_PROFILE;
  public static final PermissionGroup DEFAULT_PERMISSION_GROUP;

  static {
    DEFAULT_PERMISSION_GROUP = new PermissionGroup("OrganisationTEST", 100, new HashSet<>());
    DEFAULT_USER =
        User.builder()
            .username("exxeta")
            .email("info@exxeta.com")
            .password(Encryption.sha256("EXXETA"))
            .groups(new HashSet<>(Lists.newArrayList(DEFAULT_PERMISSION_GROUP)))
            .build();
    DEFAULT_COMPANY =
        Company.builder().name("Exxeta AG").phone("+49 721 50994-5000").user(DEFAULT_USER).build();
    DEFAULT_LOCATION =
        Location.builder()
            .street("Albert-Nestler-Straße")
            .houseNumber("19")
            .zipCode("76131")
            .city("Karlsruhe")
            .country("Deutschland")
            .build();
    DEFAULT_ADVERTISEMENT =
        Advertisement.builder()
            .deadline(Instant.now())
            .description(
                "Bei Exxeta fordern wir das traditionelle Konzept von Beratung und Tech heraus."
                    + " Über 1000 Kolleg:innen an verschiedenen Standorten schaffen jeden Tag"
                    + " gemeinsam digitale Lösungen, verändern Märkte und Mindsets – angetrieben"
                    + " von unserer Leidenschaft für Technologie, unserem Teamspirit und dem Drang,"
                    + " echten Impact zu schaffen. Hightech with a heartbeat eben. \n"
                    + "\n"
                    + "Du bist interessiert an Softwareentwicklung und hast bereits erste"
                    + " Coding-Erfahrung? Du weißt noch nicht genau, welche Rolle in der"
                    + " Entwicklung am besten zu dir passt? Dann komm zu Exxeta und wir finden es"
                    + " gemeinsam heraus. Gestalte dein Studium zusammen mit uns! Bringe dich mit"
                    + " neuen Ideen und Themen sowie deinen Stärken ein. Wir bieten keine"
                    + " festgeschriebene Position, sondern die Möglichkeit deine Rolle im"
                    + " Unternehmen selbst zu finden oder eine neue Rolle zu schaffen. Wähle"
                    + " selbst, ob du im Frontend oder Backend tätig sein willst.")
            .jobType("Werkstudent")
            .remote(true)
            .location(DEFAULT_LOCATION)
            .company(DEFAULT_COMPANY)
            .title("Werkstudent/Praktikant Softwareentwicklung (m/w/d)")
            .build();
    DEFAULT_COMPANY_PROFILE =
        CompanyProfile.builder()
            .company(DEFAULT_COMPANY)
            .description(
                "Hi!\n"
                    + "We are Exxeta... a company that challenges the traditional concept of"
                    + " consulting and tech. We love making an impact, with digital services"
                    + " powered by passion and new perspectives. Always transforming mindsets and"
                    + " markets.")
            .image(-1)
            .location(DEFAULT_LOCATION)
            .build();
    DEFAULT_STUDENT =
        Student.builder()
            .user(DEFAULT_USER)
            .phone("+49 123 456789")
            .firstName("Sascha")
            .lastName("Alda")
            .dateOfBirth(Instant.now())
            .build();
    DEFAULT_STUDENT_PROFILE =
        StudentProfile.builder()
            .student(DEFAULT_STUDENT)
            .description("B. Sc. Informatik Student")
            .location(DEFAULT_LOCATION)
            .image(-1)
            .build();
    DEFAULT_APPLY =
        Apply.builder()
            .advertisement(DEFAULT_ADVERTISEMENT)
            .applied(Instant.now())
            .note(
                "In a similar fashion, Arch ships the configuration files provided by upstream with"
                    + " changes limited to distribution-specific issues like adjusting the system"
                    + " file paths. It does not add automation features such as enabling a service"
                    + " simply because the package was installed. Packages are only split when"
                    + " compelling advantages exist, such as to save disk space in particularly bad"
                    + " cases of waste. GUI configuration utilities are not officially provided,"
                    + " encouraging users to perform most system configuration from the shell and a"
                    + " text editor. ")
            .student(DEFAULT_STUDENT)
            .build();
  }
}
