package edu.fudan.backend.controller;

import edu.fudan.backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author tyuan@ea.com
 * @Date 8/11/2020 3:35 PM
 */
@RestController
@RequestMapping("/api")
@ResponseBody
@Slf4j
public class LoginController {

    private UserService userService;

    @PostMapping("/login/account")
    public Map<String, Object> login(HttpServletResponse response, @RequestBody Map<String, Object> body) {
        Map<String, Object> result = new HashMap<>();
        String type = (String) body.get("type");
        String username = (String) body.get("userName");
        String password = (String) body.get("password");
        result.put("type", type);
        try {
            String auth = userService.authenticateUser(username, password, type);
            result.put("currentAuthority", auth);
            if (auth.equals("guest")) {
                result.put("status", "error");
            } else {
                Cookie cookie = new Cookie("username", username);
                cookie.setMaxAge(60 * 60 * 24 * 7); //保存7天
                cookie.setPath("/");
                response.addCookie(cookie);
                result.put("status", "ok");
            }
            return result;
        } catch (Exception e) {
            log.error("user auth service error!");
            result.put("status", "error");
            result.put("currentAuthority", "guest");
            return result;
        }
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
