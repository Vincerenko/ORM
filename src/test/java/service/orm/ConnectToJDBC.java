package service.orm;

import service.configs.OrmConnection;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectToJDBC implements OrmConnection
{
    private static final java.util.logging.Logger LOGGER =
            java.util.logging.Logger.getLogger(CheckDBTable.class.getName());
    @Override
    public Connection connect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ORM2", "postgres", "96321");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        LOGGER.info("Opened database successfully");
        return connection;
    }

}
