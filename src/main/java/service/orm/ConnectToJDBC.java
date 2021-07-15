package service.orm;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectToJDBC
{

public Connection connect (){


    Connection c = null;
    try {
        Class.forName("org.postgresql.Driver");
        c = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/ORM",
                        "postgres", "96321");
    } catch (Exception e) {
        e.printStackTrace();
        System.err.println(e.getClass().getName()+": "+e.getMessage());
        System.exit(0);
    }
    System.out.println("Opened database successfully");

    return c;
}

}
