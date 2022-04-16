package org.hbrs.appname.dtos.impl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hbrs.appname.dtos.RolleDTO;

@AllArgsConstructor
@NoArgsConstructor
public class RolleDTOImpl implements RolleDTO {

    private String bezeichnung;

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    @Override
    public String getBezeichnung() {
        return this.bezeichnung;
    }

    @Override
    public String toString() {
        return "RolleDTOImpl{" +
                "bezeichnung='" + bezeichnung + '\'' +
                '}';
    }
}
