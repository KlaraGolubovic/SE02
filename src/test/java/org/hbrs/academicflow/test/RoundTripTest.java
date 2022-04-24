package org.hbrs.academicflow.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import org.hbrs.academicflow.model.user.User;
import org.hbrs.academicflow.model.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RoundTripTest {

  @Autowired private UserRepository userRepository;

  @Test
  /**
   * Round Triping Test mit einer einfachen Strecke (C-R-Ass-D). Dieses Muster für Unit-Tests wird
   * in der Vorlesung SE-2 eingeführt (Kapitel 6).
   */
  void createReadAndDeleteAUser() {

    // Schritt 1: C = Create (hier: Erzeugung und Abspeicherung mit der Method
    // save()
    // Anlegen eines Users. Eine ID wird automatisch erzeugt durch JPA
    User user = new User();
    user.setEmail("testZWEsIdds@myserver.de");
    user.setFirstName("Torben");
    user.setLastName("Michel");
    // und ab auf die DB damit (save!)
    User userAfterCreate = userRepository.save(user);

    // Da die ID auto-generiert wurde, müssen wir uns die erzeugte ID nach dem
    // Abspeichern merken:
    int idTmp = userAfterCreate.getUser_id();

    // Schritt 2: R = Read (hier: Auslesen über die Methode find()

    // Schritt 3: Ass = Assertion: Vergleich der vorhandenen Objekte auch
    // Gleichheit...
    assertEquals("Michel", userAfterCreate.getLastName());
    assertEquals("Torben", userAfterCreate.getFirstName());
    // ... sowie auf Identität
    assertNotSame(user, userAfterCreate);

    // Schritt 4: D = Deletion, also Löschen des Users, um Datenmüll zu vermeiden
    userRepository.deleteById(idTmp);
    // Schritt 4.1: Wir sind vorsichtig und gucken, ob der User wirklich gelöscht
    // wurde ;-)
    Optional<User> wrapperAfterDelete = userRepository.findById(idTmp);
    System.out.println("Wrapper: " + wrapperAfterDelete);
    assertFalse(wrapperAfterDelete.isPresent());
  }

  @AfterEach
  public void deleteUser() {
    // Hier könnte man nach einem RoundTrip die DB noch weiter bereinigen
  }
}
