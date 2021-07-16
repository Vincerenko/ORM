import entities.User;
import service.orm.OrmManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main
{
    public static void main(String[] args) throws SQLException {
        //ConnectToJDBC connectToJDBC = new ConnectToJDBC();
        //connectToJDBC.connect();

        //        Statement statement = c.createStatement();
        //        statement.executeUpdate("CREATE TABLE products (Id INT PRIMARY KEY, ProductName VARCHAR(20), Price INT)");
        User user = new User("Many",21);
        OrmManager ormManager = new OrmManager();
        ormManager.save(user);


//        Connection connection = null;
//        try {
//            Class.forName("org.postgresql.Driver");
//            connection = DriverManager
//                    .getConnection("jdbc:postgresql://localhost:5432/ORM",
//                            "postgres", "96321");
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.err.println(e.getClass().getName() + ": " + e.getMessage());
//            System.exit(0);
//        }
//        System.out.println("Opened database successfully");

        //Statement statement = connection.createStatement();
        //statement.executeUpdate("CREATE TABLE "+user.getName()+" ( AGE int )");
    }


}

