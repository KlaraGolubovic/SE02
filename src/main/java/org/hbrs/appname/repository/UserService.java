package org.hbrs.appname.repository;

import lombok.RequiredArgsConstructor;
import org.hbrs.appname.dtos.RolleDTO;
import org.hbrs.appname.dtos.UserDTO;
import org.hbrs.appname.dtos.impl.RolleDTOImpl;
import org.hbrs.appname.dtos.impl.UserDTOImpl;
import org.hbrs.appname.entities.PermissionGroup;
import org.hbrs.appname.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {
    private final UserRepository repository;

    public UserDTO findUserByIdAndPassword(String id, String password) {
        final User user = this.repository.findUserByIdAndPassword(id, password);
        final List<RolleDTO> roles = user.getGroups()
                .stream()
                .map(PermissionGroup::getName)
                .map(RolleDTOImpl::new)
                .collect(Collectors.toList());
        return new UserDTOImpl(user.getId(), user.getFirstName(), user.getLastName(), roles);
    }
}
