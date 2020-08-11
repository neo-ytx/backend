package edu.fudan.backend.dao;

import edu.fudan.backend.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;


/**
 * @Author tyuan@ea.com
 * @Date 8/11/2020 2:51 PM
 */


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void saveUser() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("ant.design");
        user.setNickname("ytx");
        userRepository.save(user);
    }

    @Test
    void findByUsername() {
        User user = userRepository.findByUsername("admin");
        System.out.println(user.getNickname());
    }

    @Test
    void findByNickname() {
        User user = userRepository.findByNickname("ytx");
        System.out.println(user.getUsername());
    }

    @Test
    void findByUserId() {
        Optional<User> userOptional = userRepository.findById(1);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            System.out.println(user.getNickname() + " " + user.getUsername());
        }
    }
}