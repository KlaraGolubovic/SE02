package org.hbrs.appname.control;

import org.hbrs.appname.control.factories.UserFactory;
import org.hbrs.appname.dtos.impl.UserDTOImpl;
import org.hbrs.appname.entities.User;
import org.hbrs.appname.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserControl {

    @Autowired
    private UserRepository repository;

    public void createUser(UserDTOImpl u) {

        User user = UserFactory.createUser(u);

        // Schritt 1: C = Create (hier: Erzeugung und Abspeicherung mit der Method
        // save()
        // Anlegen eines Users. Eine ID wird automatisch erzeugt durch JPA

        repository.save(user);

    }

}
