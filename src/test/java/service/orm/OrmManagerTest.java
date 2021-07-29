package service.orm;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import orm.entities.Firm;
import orm.entities.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class OrmManagerTest
{
    private final OrmManager<User, String> ormManagerU = new OrmManager<>(User.class, String.class, new ConnectToJDBC());
    private final OrmManager<Firm, Long> ormManagerF = new OrmManager<>(Firm.class, Long.class, new ConnectToJDBC());

    //    @org.junit.jupiter.api.BeforeAll
    //    void setUp() {
    //        ormManagerU =
    //        ormManagerF =
    //    }
    //
    //
    //
    //    @org.junit.jupiter.api.AfterAll
    //    void tearDown() {
    //        ormManagerU = null;
    //        ormManagerF = null;
    //    }
    @AfterEach
    void clear() throws SQLException {
        ormManagerU.clear("students");
        ormManagerU.clear("firm");
    }


    @Test
    void saveFirm() {
        Firm firm = new Firm("Pepsi", "100к");
        ormManagerF.save(firm);
        Firm firm1 = ormManagerF.getById(firm.getId());
        assertEquals(firm, firm1);

    }

    @Test
    void saveUser() {
        User user = new User("Vasy", "Sanin", 100);

        ormManagerU.save(user);
        User user1 = (User) ormManagerU.getById(user.getId());

        assertEquals(user.getName(), user1.getName());
    }

    @Test
    void deleteById() {
        Firm firm = new Firm("Test", "100");
        Firm save = ormManagerF.save(firm);
        assertEquals(save.getName(), ormManagerF.getById(save.getId()).getName());
        ormManagerF.deleteById(save.getId());
        assertNull(ormManagerF.getById(save.getId()));
    }

    @Test
    void getById() {
        Firm firm = new Firm("Pepsi", "100к");
        ormManagerF.save(firm);
        Firm firm1 = ormManagerF.getById(firm.getId());
        assertEquals(firm, firm1);


    }

    @Test
    void selectAll() {
        assertEquals(0, ormManagerF.selectAll().size());
        Firm firm = new Firm("Pepsi", "100к");
        Firm firm1 = new Firm("Cola", "200к");
        Firm firm2 = new Firm("Fanta", "300к");
        ormManagerF.save(firm);
        ormManagerF.save(firm1);
        ormManagerF.save(firm2);
        assertEquals(3, ormManagerF.selectAll().size());
    }

    @Test
    void update() {
        Firm firm = new Firm("Calgon", "1000к");
        Firm save = ormManagerF.save(firm);
        assertEquals(firm.getName(), ormManagerF.getById(save.getId()).getName());
        save.setName("Nike");
        ormManagerF.save(save);
        assertEquals(save.getName(), ormManagerF.getById(save.getId()).getName());
    }

    @Test
    void saveAll() {
        List<Firm> firmList = new ArrayList<>();
        assertEquals(0, ormManagerF.selectAll().size());
        Firm firm = new Firm("Pepsi", "100к");
        Firm firm1 = new Firm("Cola", "200к");
        Firm firm2 = new Firm("Fanta", "300к");
        firmList.add(firm);
        firmList.add(firm1);
        firmList.add(firm2);
        ormManagerF.saveAll(firmList);
        assertEquals(3, ormManagerF.selectAll().size());
    }

}