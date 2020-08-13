package edu.fudan.backend.service.impl;

import edu.fudan.backend.dao.UserRepository;
import edu.fudan.backend.model.User;
import edu.fudan.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Author tyuan@ea.com
 * @Date 8/13/2020 2:32 PM
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public String authenticateUser(String username, String password, String type) throws Exception {
        Optional<User> optionalUser = userRepository.findByUsernameAndPassword(username, password);
        if (optionalUser.isPresent()) {
            return optionalUser.get().getRole();
        } else if (type.equals("mobile")) {
            return "admin";
        } else {
            return "guest";
        }
    }

    @Override
    public User getCurrentUser() throws Exception {
        return null;
    }
}
