package org.hbrs.academicflow.control;

import java.util.List;

import org.hbrs.academicflow.model.user.User;
import org.hbrs.academicflow.model.user.UserRepository;
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
