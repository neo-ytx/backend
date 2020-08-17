package edu.fudan.backend.controller;

import edu.fudan.backend.model.User;
import edu.fudan.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author tyuan@ea.com
 * @Date 8/13/2020 2:49 PM
 */
@RestController
@RequestMapping("/api")
@ResponseBody
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/currentUser", method = RequestMethod.GET)
    public Map<String, Object> getCurrentUser(@CookieValue("username") String username) {

        Map<String, Object> result = new HashMap<>();
        try {
            User user = userService.getCurrentUser(username);
            result.put("name", user.getNickname());
            result.put("avatar", "https://gw.alipayobjects.com/zos/antfincdn/XAosXuNZyF/BiazfanxmamNRoxxVxka.png");
            result.put("userid", user.getUsername());
            result.put("email", user.getEmail());
            result.put("signature", "干");
            result.put("title", "研究生");
            result.put("group", "609实验室");
            result.put("country", "China");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

}
