package edu.fudan.backend.controller;

import edu.fudan.backend.dao.UserRepository;
import edu.fudan.backend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @Author tyuan@ea.com
 * @Date 8/11/2020 3:35 PM
 */
@RestController
@RequestMapping("/api")
@ResponseBody
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login/account")
    public Map<String, Object> login(@RequestBody Map<String, Object> body) {
        Map<String, Object> result = new HashMap<>();
        String type = (String) body.get("type");
        result.put("type", type);
        Optional<User> optionalUser = userRepository.findByUsernameAndPassword((String) body.get("username"), (String) body.get("password"));
        if (optionalUser.isPresent()) {
            result.put("status", "ok");
            result.put("currentAuthority", "admin");
            return result;
        } else if (type.equals("mobile")) {
            result.put("status", "ok");
            result.put("currentAuthority", "admin");
        }
        result.put("status", "error");
        result.put("currentAuthority", "guest");
        return result;
    }
}
