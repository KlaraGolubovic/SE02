package org.hbrs.appname.dtos.impl;

import java.util.List;

import org.hbrs.appname.dtos.RolleDTO;
import org.hbrs.appname.dtos.UserDTO;

public class UserDTOImpl implements UserDTO {

    private int id;
    private String firstname;
    private String lastname;
    private List<RolleDTO> roles;

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setRoles(List<RolleDTO> roles) {
        this.roles = roles;
    }


    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getFirstName() {
        return this.firstname;
    }

    @Override
    public String getLastName() {
        return this.lastname;
    }

    @Override
    public List<RolleDTO> getRoles() {
        return this.roles;
    }

    @Override
    public String toString() {
        return "UserDTOImpl{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", roles=" + roles +
                '}';
    }
}
