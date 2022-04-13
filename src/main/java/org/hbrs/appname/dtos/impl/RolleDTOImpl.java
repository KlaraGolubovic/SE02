package org.hbrs.appname.dtos.impl;

import org.hbrs.appname.dtos.RolleDTO;

public class RolleDTOImpl implements RolleDTO {

    private String bezeichnung;

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    @Override
    public String getBezeichhnung() {
        return this.bezeichnung;
    }

    @Override
    public String toString() {
        return "RolleDTOImpl{" +
                "bezeichnung='" + bezeichnung + '\'' +
                '}';
    }
}
