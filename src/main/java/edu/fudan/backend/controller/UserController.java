package edu.fudan.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(value = "/currentUser", method = RequestMethod.GET)
    public Map<String, Object> getCurrentUser() {
        Map<String, Object> result = new HashMap<>();
        result.put("name", "Yuan Tianxing");
        result.put("avatar", "https://gw.alipayobjects.com/zos/antfincdn/XAosXuNZyF/BiazfanxmamNRoxxVxka.png");
        result.put("userid", "00000001");
        result.put("email", "admin@example.com");
        result.put("signature", "干");
        result.put("title", "研究生");
        result.put("group", "609实验室");
        result.put("country", "China");
        return result;
    }

}
