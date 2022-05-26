package com.mycompany;

import com.mycompany.User.User;
import com.mycompany.User.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@DataJpaTest
//test the data repository functions in memory database
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UsereRepositoryTests {
    @Autowired private UserRepository repo;

    @Test
    public void testAddnew(){
        User user = new User();
        user.setEmail("ajay.kumar@gmail.com");
        user.setPassword("ajay@12345");
        user.setFirstName("Ajay");
        user.setLastName("Kumar");
        User savedUser = repo.save(user);
        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);
    }
    @Test
    public void testListAll(){
        Iterable<User> users = repo.findAll();
        Assertions.assertThat(users).hasSizeGreaterThan(0);
        for(User user: users){
            System.out.println(user);
        }
    }
    @Test
    public void testUpdate(){
        Integer userId =1;
        Optional<User> optionalUser = repo.findById(userId);
        User user = optionalUser.get();
        user.setPassword("hello2000");
        repo.save(user);

        User updateUser =  repo.findById(userId).get();
        Assertions.assertThat(updateUser.getPassword()).isEqualTo("hello2000");
    }
    @Test
    public void testGet(){
        Integer userId =  2;
        Optional<User> optionalUser = repo.findById(userId);
        Assertions.assertThat(optionalUser).isPresent();
        System.out.println(optionalUser);
    }
    @Test
    public  void testDelete(){
        Integer userId = 3;
        repo.deleteById(userId);

        Optional<User> optionalUser = repo.findById(userId);
        Assertions.assertThat(optionalUser).isNotPresent();
    }
}
