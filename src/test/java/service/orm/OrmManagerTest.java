package service.orm;

import annotations.Id;
import orm.entities.Firm;
import orm.entities.User;

import static org.junit.jupiter.api.Assertions.*;

class OrmManagerTest
{
    OrmManager ormManager = null;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
         ormManager = new OrmManager(User.class, String.class);

    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        ormManager = null;
    }

    @org.junit.jupiter.api.Test
    void save() {
        Firm firm = new Firm("Coca","250k");
        User user =new User("Vasy","Sanin",100);

        assertEquals(user,ormManager.save(user));
    }

    @org.junit.jupiter.api.Test
    void deleteById() {

    }

    @org.junit.jupiter.api.Test
    void getById() {

        User user1;
        assertEquals(user1 =new User("4adbb01d-8741-4c8d-b890-7d2ae89ae494","Vasy","Sanin",100), ormManager.getById("4adbb01d-8741-4c8d-b890-7d2ae89ae494"));


    }

    @org.junit.jupiter.api.Test
    void selectAll() {
    }

    @org.junit.jupiter.api.Test
    void update() {
        User user =new User("Vas","San",200);
        //ormManager.update("4adbb01d-8741-4c8d-b890-7d2ae89ae494",user);
        assertEquals(user,ormManager.update("4adbb01d-8741-4c8d-b890-7d2ae89ae494",user));
    }


}