package org.hbrs.academicflow.control;

import lombok.RequiredArgsConstructor;
import org.hbrs.academicflow.model.user.UserFactory;
import org.hbrs.academicflow.model.user.dto.UserDTO;
import org.hbrs.academicflow.model.user.User;
import org.hbrs.academicflow.model.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserControl {
    private final UserRepository repository;

    public void createUser(UserDTO dto) {
        User user = UserFactory.createUser(dto);
        // Schritt 1: C = Create (hier: Erzeugung und Abspeicherung mit der Method
        // save()
        // Anlegen eines Users. Eine ID wird automatisch erzeugt durch JPA
        repository.save(user);
    }
}
