package org.hbrs.appname.services.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hbrs.appname.services.db.exceptions.DatabaseLayerException;

public class SQLiteConnection {
    
    private static SQLiteConnection connection = null;

    private String url = "jdbc:sqlite:src/main/java/org/hbrs/appname/services/db/local.db";

    private Connection conn;

    private String login = "demouser";

    private String password = "demouser";

    public static SQLiteConnection getInstance() throws DatabaseLayerException {

        if (connection == null) {
            connection = new SQLiteConnection();
        }
        return connection;

    }

    private SQLiteConnection() throws DatabaseLayerException {

        this.initConnection();

    }

    

    public void initConnection() throws DatabaseLayerException {
        try {

            DriverManager.registerDriver(new org.postgresql.Driver());
        } catch (SQLException ex) {
            Logger.getLogger(SQLiteConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.openConnection();

    }

    public void openConnection() throws DatabaseLayerException {

        try {
            Properties props = new Properties();
            props.setProperty("user", "demouser");
            props.setProperty("password", "demouser");

            this.conn = DriverManager.getConnection(this.url, props);

        } catch (SQLException ex) {
            Logger.getLogger(SQLiteConnection.class.getName()).log(Level.SEVERE, null, ex);
            throw new DatabaseLayerException("Fehler bei Zugriff auf die DB! Sichere Verbindung vorhanden!?");
        }
    }

    public Statement getStatement() throws DatabaseLayerException {

        try {
            if (this.conn.isClosed()) {
                this.openConnection();
            }

            return this.conn.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(SQLiteConnection.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    public PreparedStatement getPreparedStatement(String sql) throws DatabaseLayerException {
        try {
            if (this.conn.isClosed()) {
                this.openConnection();
            }

            return this.conn.prepareStatement(sql);
        } catch (SQLException ex) {
            Logger.getLogger(SQLiteConnection.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    public void closeConnection() {
        try {
            this.conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLiteConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
