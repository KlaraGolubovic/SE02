package org.hbrs.appname.repository;

import org.hbrs.appname.dtos.UserDTO;
import org.hbrs.appname.entities.Rolle;
import org.hbrs.appname.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
/**
 * JPA-Repository für die Abfrage der Rollen von registrierten User. Die Bezeichnung einer Methode
 * bestimmt dabei die Selektionsbedingung (den WHERE-Teil). Der Rückgabewert einer
 * Methode bestimmt den Projectionsbedingung (den SELECT-Teil).
 * Mehr Information über die Entwicklung von Queries in JPA:
 * https://www.baeldung.com/spring-data-jpa-projections
 * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods
 *
 */
public interface RolleRepository extends JpaRepository<Rolle, Integer> {

}