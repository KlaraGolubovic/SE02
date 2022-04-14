package org.hbrs.appname.test;

import org.hbrs.appname.dao.UserDAO;
import org.hbrs.appname.dtos.UserDTO;
import org.hbrs.appname.services.db.exceptions.DatabaseLayerException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class HellocarApplicationTests {

    @Test
    void testFindUserWithJDBC() {
        UserDAO userDAO = new UserDAO();
        try {
            UserDTO userDTO = userDAO.findUserByUseridAndPassword("sascha", "abc");
            System.out.println(userDTO.toString());

            assertEquals("Sascha", userDTO.getFirstName());
        } catch (DatabaseLayerException e) {
            e.printStackTrace();
        }

    }

}
