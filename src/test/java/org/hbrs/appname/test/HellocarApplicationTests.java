package org.hbrs.appname.test;

import org.hbrs.appname.dao.UserDAO;
import org.hbrs.appname.dtos.CarDTO;
import org.hbrs.appname.dtos.RolleDTO;
import org.hbrs.appname.dtos.UserDTO;
import org.hbrs.appname.entities.Rolle;
import org.hbrs.appname.entities.User;
import org.hbrs.appname.repository.CarRepository;
import org.hbrs.appname.repository.RolleRepository;
import org.hbrs.appname.repository.UserRepository;
import org.hbrs.appname.services.db.exceptions.DatabaseLayerException;
import org.hbrs.appname.util.Utils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

@SpringBootTest
class HellocarApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolleRepository roleRepository;

    @Autowired
    private CarRepository carRepository;

    @Test
    void testRolesOfUser() {
        Optional<User> wrapper = userRepository.findById(1);
        if (wrapper.isPresent()) {
            User user = wrapper.get();
            System.out.println("User: " + user.getLastName());
            List<Rolle> list = user.getRoles();
            assertEquals(2, list.size(), "Anzahl der Rollen");
            Rolle rolle1 = list.get(0);
            assertEquals("admin", rolle1.getBezeichhnung());
        }
    }

    @Test
    void testUserDTOByAttribute() {
        UserDTO personDTO = userRepository.getUserByOccupation("Professor").get(0);
        System.out.println(personDTO.getFirstName());
        assertEquals("Sascha", personDTO.getFirstName());
        assertEquals(1, personDTO.getId());
    }

    @Test
    void testUserDTOByPassword() {
        UserDTO userDTO = userRepository.findUserByUseridAndPassword("sascha", "abc");
        System.out.println(userDTO.getFirstName());
        assertEquals("Sascha", userDTO.getFirstName());
    }

    @Test
    void testUserDTOAndItsRoles() {
        UserDTO userDTO = userRepository.findUserByUseridAndPassword("sascha", "abc");
        System.out.println(userDTO.getFirstName());
        assertEquals("Sascha", userDTO.getFirstName());
        List<RolleDTO> list = userDTO.getRoles();
        System.out.println(list.size());
        assertEquals(2, list.size());
    }

    @Test
    void testPersonLoad() {
        Optional<User> wrapper = userRepository.findById(1);
        if (wrapper.isPresent()) {
            User user = wrapper.get();
            assertEquals("Alda", user.getLastName());
        }
    }

    @Test
    void testRoleRepository() {
        List<Rolle> list = roleRepository.findAll();
        String[] soll = { "admin", "user" };
        String[] ist = {};

        for (Rolle r : list) {
            System.out.println("Rolle: " + r.getBezeichhnung());
            ist = Utils.append(ist, r.getBezeichhnung());
        }
        assertArrayEquals(soll, ist);
    }

    @Test
    void testFindCarsAndTheirUsers() {
        List<Object[]> list = this.carRepository.findAllCarsAndTheirUsers();
        for (Object[] item : list) {
            System.out.println("Brand: " + item[0]);
            System.out.println("Model: " + item[1]);
            System.out.println("Price: " + item[2]);
            System.out.println("First Name: " + item[3]);
            System.out.println("Last Name: " + item[4]);
        }
        // Todo: Definition von passenden Assertions
    }

    @Test
    void testFindCarsWithMostImportantValues() {
        List<CarDTO> list = this.carRepository.findCarsByDateIsNotNull();
        for (CarDTO item : list) {
            System.out.println("Brand: " + item.getBrand());
            System.out.println("Model: " + item.getModel());
            System.out.println("Price: " + item.getPrice());
            System.out.println("Phone: " + item.getPhone());
        }
        // Todo: Definition von passenden Assertions
    }

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
