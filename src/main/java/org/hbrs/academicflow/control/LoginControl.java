package org.hbrs.academicflow.control;

import org.hbrs.academicflow.control.exception.DatabaseUserException;
import org.hbrs.academicflow.model.user.dto.UserDTO;
import org.hbrs.academicflow.model.user.UserService;
import org.hbrs.academicflow.util.Encryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;

@Component
public class LoginControl {
    @Autowired
    private UserService service;

    private UserDTO userDTO = null;

    public boolean authentificate(String username, String password) throws DatabaseUserException {
        // Standard: User wird mit Spring JPA ausgelesen (Was sind die Vorteile?)
        UserDTO tmpUser = null;
        try {
            tmpUser = this.getUserWithJPA(username, password);
        } catch (NoSuchAlgorithmException e) {
            return false;
        }

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

    private UserDTO getUserWithJPA(String username, String password) throws DatabaseUserException, NoSuchAlgorithmException {
        UserDTO userTmp;
        try {
            //userTmp = this.service.findUserByIdAndPassword(username, password);
            userTmp = this.service.findUserByIdAndPassword(username, Encryption.sha256(password));
        } catch (org.springframework.dao.DataAccessResourceFailureException e) {
            throw new DatabaseUserException("A failure occurred while trying to connect to database with JPA");
        }
        return userTmp;
    }
}
