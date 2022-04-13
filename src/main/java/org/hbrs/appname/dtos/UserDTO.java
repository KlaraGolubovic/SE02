package org.hbrs.appname.dtos;

import java.util.List;

import org.hbrs.appname.entities.Rolle;

public interface UserDTO {
    public int getId();
    public String getFirstName();
    public String getLastName();
    public List<RolleDTO> getRoles();
}
