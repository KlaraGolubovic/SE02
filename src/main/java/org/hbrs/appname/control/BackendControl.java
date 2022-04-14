package org.hbrs.appname.control;

import java.util.List;

import org.hbrs.appname.control.factories.UserFactory;
import org.hbrs.appname.dtos.impl.UserDTOImpl;
import org.hbrs.appname.entities.User;
import org.hbrs.appname.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BackendControl {

    @Autowired
    private UserRepository repository;

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    

}
