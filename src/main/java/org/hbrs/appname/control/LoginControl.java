package org.hbrs.appname.control;

import org.hbrs.appname.control.exception.DatabaseUserException;
import org.hbrs.appname.model.user.dto.UserDTO;
import org.hbrs.appname.model.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginControl {
    @Autowired
    private UserService service;

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

    private UserDTO getUserWithJPA(String username, String password) throws DatabaseUserException {
        UserDTO userTmp;
        try {
            userTmp = this.service.findUserByIdAndPassword(username, password);
        } catch (org.springframework.dao.DataAccessResourceFailureException e) {
            throw new DatabaseUserException("A failure occurred while trying to connect to database with JPA");
        }
        return userTmp;
    }
}
