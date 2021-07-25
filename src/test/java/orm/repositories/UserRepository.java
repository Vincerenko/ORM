package orm.repositories;


import orm.entities.User;
import service.orm.OrmManager;

public class UserRepository extends OrmManager<User, String> {
    public UserRepository() {
        super(User.class, String.class);
    }
}
