package org.hbrs.se2.project.hellocar.control;

import org.hbrs.se2.project.hellocar.control.factories.UserFactory;
import org.hbrs.se2.project.hellocar.dtos.impl.UserDTOImpl;

import org.hbrs.se2.project.hellocar.entities.User;
import org.hbrs.se2.project.hellocar.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserControl {
    
    @Autowired
    private UserRepository repository;


    public void createUser(UserDTOImpl u ) {
        // Hier könnte man noch die Gültigkeit der Daten überprüfen
        // check( carDTO );

        //Erzeuge ein neues Car-Entity konsistent über eine Factory
        User user = UserFactory.createUser(u);
        // Abspeicherung des Entity in die DB
        this.repository.save( user );
    }


}
