package org.hbrs.appname.control;

import org.hbrs.appname.control.exception.DatabaseUserException;
import org.hbrs.appname.dao.UserDAO;
import org.hbrs.appname.dtos.UserDTO;
import org.hbrs.appname.repository.UserRepository;
import org.hbrs.appname.services.db.exceptions.DatabaseLayerException;
import org.hbrs.appname.util.Globals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginControl {

    @Autowired
    private UserRepository repository;

    private UserDTO userDTO = null;

    public boolean authentificate(String username, String password) throws DatabaseUserException {
        // Standard: User wird mit Spring JPA ausgelesen (Was sind die Vorteile?)
        UserDTO tmpUser = this.getUserWithJPA(username, password);

        // Alternative: Auslesen des Users mit JDBC (Was sind die Vorteile bzw.
        // Nachteile?)
        // UserDTO tmpUser = this.getUserWithJDBC( username , password );

        if (tmpUser == null) {
            // ggf. hier ein Loggin einfügen
            return false;
        }
        this.userDTO = tmpUser;
        return true;
    }

    public UserDTO getCurrentUser() {
        return this.userDTO;

    }

    private UserDTO getUserWithJDBC(String username, String password) throws DatabaseUserException {
        UserDTO userTmp = null;
        UserDAO dao = new UserDAO();
        try {
            userDTO = dao.findUserByUseridAndPassword(username, password);
        } catch (DatabaseLayerException e) {

            // Analyse und Umwandlung der technischen Errors in 'lesbaren' Darstellungen
            // Durchreichung und Behandlung der Fehler (Chain Of Responsibility Pattern
            // (SE-1))
            String reason = e.getReason();

            if (reason.equals(Globals.Errors.NOUSERFOUND)) {
                return userTmp;
                // throw new DatabaseUserException("No User could be found! Please check your
                // credentials!");
            } else if (reason.equals((Globals.Errors.SQLERROR))) {
                throw new DatabaseUserException("There were problems with the SQL code. Please contact the developer!");
            } else if (reason.equals((Globals.Errors.DATABASE))) {
                throw new DatabaseUserException("A failure occured while trying to connect to database with JDBC. " +
                        "Please contact the admin");
            } else {
                throw new DatabaseUserException("A failure occured while");
            }

        }
        return userDTO;
    }

    private UserDTO getUserWithJPA(String username, String password) throws DatabaseUserException {
        UserDTO userTmp;
        try {
            userTmp = repository.findUserByUseridAndPassword(username, password);
        } catch (org.springframework.dao.DataAccessResourceFailureException e) {
            // Analyse und Umwandlung der technischen Errors in 'lesbaren' Darstellungen
            // (ToDo!)
            throw new DatabaseUserException("A failure occured while trying to connect to database with JPA");
        }
        return userTmp;
    }

}
