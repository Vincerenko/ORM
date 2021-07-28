package service.orm;

import orm.entities.User;

public class Main
{
    public static void main(String[] args) {
        OrmManager<User,String> ormManager = new OrmManager<>(User.class, String.class);
        User user =new User("Vasy","Sanin",101);
        System.out.println(ormManager.save(user));
    }
}
