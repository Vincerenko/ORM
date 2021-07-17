import entities.User;
import service.orm.OrmManager;

import java.sql.SQLException;

public class Main
{
    public static void main(String[] args) throws SQLException {
        User user = new User("Sanches2","Sanin", 45);
        OrmManager ormManager = new OrmManager();
        System.out.println(ormManager.save(user));
        ormManager.update();


    }


}

