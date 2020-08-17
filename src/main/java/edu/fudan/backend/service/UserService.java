package edu.fudan.backend.service;

import edu.fudan.backend.model.User;

/**
 * @Author tyuan@ea.com
 * @Date 8/13/2020 2:32 PM
 */
public interface UserService {
    String authenticateUser(String username, String password, String type) throws Exception;

    User getCurrentUser(String username) throws Exception;
}
