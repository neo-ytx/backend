package edu.fudan.backend.dao;

import edu.fudan.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author tyuan@ea.com
 * @Date 8/11/2020 2:47 PM
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);

    User findByNickname(String nickname);

}
