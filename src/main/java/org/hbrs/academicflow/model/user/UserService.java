package org.hbrs.academicflow.model.user;

import lombok.RequiredArgsConstructor;
import org.hbrs.academicflow.model.user.dto.UserDTO;
import org.hbrs.academicflow.model.user.dto.UserDTOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<User> findAllUsers() {
        return this.repository.findAll();
    }
}
