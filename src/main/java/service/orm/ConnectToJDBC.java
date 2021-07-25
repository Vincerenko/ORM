package service.orm;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectToJDBC {

    public Connection connect() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ORM2", "postgres", "96321");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
        return connection;
    }

}
