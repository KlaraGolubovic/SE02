package org.hbrs.appname.model.user;

import lombok.RequiredArgsConstructor;
import org.hbrs.appname.model.user.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {
    private final UserRepository repository;

    public UserDTO findUserByIdAndPassword(String id, String password) {
        return this.repository.findUserByIdAndPassword(id, password);
    }

    public UserDTO findUserById(String id) {
        return this.repository.findUserByUserId(id);
    }

    public User doCreateUser(User user) {
        return this.repository.save(user);
    }

    public void deleteUser(String userId) {
        this.repository.deleteUserByUserId(userId);
    }
}
