package tests;

import org.junit.Test;
import orm.entities.User;
import orm.repositories.UserRepository;

public class TestOrmUser {

    private final UserRepository userRepository = new UserRepository();

    @Test //GOOD
    public void testCreate(){
        userRepository.save(new User("San","555",54));
    }

    @Test //GOOD
    public void testUpdate(){
        userRepository.save(new User("0628c54f-074a-418f-842c-b2c30f2343c2","San","sss",90));
    }

    @Test //GOOD
    public void testGetById(){
        System.out.println(userRepository.getById("8de79949-f619-4db0-b9c2-584121fdeb10"));
    }

    @Test //GOOD
    public void testDeleteById(){
        userRepository.deleteById("0628c54f-074a-418f-842c-b2c30f2343c2");
    }

    @Test //GOOD
    public void testSelectAll(){
        System.out.println(userRepository.selectAll());
    }
}
