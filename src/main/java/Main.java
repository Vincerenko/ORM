import entities.User;
import service.orm.ConnectToJDBC;
import service.orm.OrmManager;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws SQLException {
        ConnectToJDBC connectToJDBC = new ConnectToJDBC();
        connectToJDBC.connect();

//        Statement statement = c.createStatement();
//        statement.executeUpdate("CREATE TABLE products (Id INT PRIMARY KEY, ProductName VARCHAR(20), Price INT)");

//        OrmManager ormManager = new OrmManager();
//        ormManager.save(new User( "Petya",19));
    }




    }

