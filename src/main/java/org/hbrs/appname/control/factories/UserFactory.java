package org.hbrs.appname.control.factories;

import org.hbrs.appname.dtos.UserDTO;
import org.hbrs.appname.entities.User;

public class UserFactory {
    private UserFactory() {
        // The factory can only be used statically.
    }

    public static User createUser(UserDTO userDTO) {
        User user = new User();
        user.setEmail("test@test.de");
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        return user;
    }
}
