package org.hbrs.appname.control;

import lombok.RequiredArgsConstructor;
import org.hbrs.appname.model.user.UserFactory;
import org.hbrs.appname.model.user.dto.UserDTO;
import org.hbrs.appname.model.user.dto.UserDTOImpl;
import org.hbrs.appname.model.user.User;
import org.hbrs.appname.model.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
