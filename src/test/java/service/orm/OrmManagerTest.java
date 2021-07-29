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
        ormManager.save(firm);

        assertEquals(user,ormManager.save(user));
    }

    @org.junit.jupiter.api.Test
    void deleteById() {
        ormManager.deleteById("7efa01b8-8cc6-43e2-a4a1-9651628fa5e8");
    }

    @org.junit.jupiter.api.Test
    void getById() {

        User user1 =new User("ddc1aad1-ec0c-4f5a-984b-fd7582215c2d","Vasy","Sanin",101);
        Object user2 = ormManager.getById("ddc1aad1-ec0c-4f5a-984b-fd7582215c2d");
        ormManager.save(user1);
        assertEquals(user1,ormManager.getById("ddc1aad1-ec0c-4f5a-984b-fd7582215c2d") );


    }

    @org.junit.jupiter.api.Test
    void selectAll() {
        System.out.println(ormManager.selectAll());
    }

    @org.junit.jupiter.api.Test
    void update() {
        User user =new User("Vas","San",200);
        //ormManager.update("4adbb01d-8741-4c8d-b890-7d2ae89ae494",user);
        assertEquals(user,ormManager.update("4adbb01d-8741-4c8d-b890-7d2ae89ae494",user));
    }


}