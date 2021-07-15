import entities.User;
import service.orm.OrmManager;

public class Main {
    public static void main(String[] args) {
        OrmManager ormManager = new OrmManager();
        ormManager.save(new User( "Petya",19));
    }
}
