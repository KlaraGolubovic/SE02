package org.hbrs.appname.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.hbrs.appname.dtos.RolleDTO;
import org.hbrs.appname.dtos.UserDTO;
import org.hbrs.appname.dtos.impl.RolleDTOImpl;
import org.hbrs.appname.services.db.JDBCConnection;
import org.hbrs.appname.services.db.SQLiteConnection;
import org.hbrs.appname.services.db.exceptions.DatabaseLayerException;

/**
 * Bereitstellung einer Schnittstelle für den Zugriff auf Rollen in der
 * Datenbank
 * Realisierung einer CRUD-Schnittstelle (hier: nur Read (get...)) --> SE-2
 *
 */
public class RolleDAO {
    public List<RolleDTO> getRolesOfUser(UserDTO userDTO) throws DatabaseLayerException {
        ResultSet set = null;

        try {
            Statement statement = null;
            try {
                //statement = JDBCConnection.getInstance().getStatement();
                statement = SQLiteConnection.getInstance().getStatement();

            } catch (DatabaseLayerException e) {
                e.printStackTrace();
            }

            set = statement.executeQuery(
                    "SELECT * "
                            + "FROM carlook.user_to_rolle "
                            + "WHERE carlook.user_to_rolle.userid = \'" + userDTO.getId() + "\'");

        } catch (SQLException ex) {

            throw new DatabaseLayerException("Fehler im SQL-Befehl! Bitte den Programmier benachrichtigen!");
        }

        if (set == null)
            return null;

        List<RolleDTO> liste = new ArrayList<RolleDTO>();
        RolleDTOImpl role = null;

        try {
            while (set.next()) {

                role = new RolleDTOImpl();
                // Object Relation Mapping (ORM)
                role.setBezeichnung(set.getString(2));
                liste.add(role);

            }
        } catch (SQLException ex) {
            throw new DatabaseLayerException("Fehler im SQL-Befehl! Bitte den Programmier benachrichtigen!");
        }
        return liste;

    }
}
