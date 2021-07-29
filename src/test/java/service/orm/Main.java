package service.orm;

import orm.entities.User;

public class Main
{
    public static void main(String[] args) {
        OrmManager<User,String> ormManager = new OrmManager<>(User.class, String.class);
//        User user =new User("Vasy","Sanin",101);
//        System.out.println(ormManager.save(user));
        System.out.println(ormManager.getById("ddc1aad1-ec0c-4f5a-984b-fd7582215c2d"));
    }
}
