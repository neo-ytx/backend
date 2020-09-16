package edu.fudan.backend.controller;

import edu.fudan.backend.model.Image;
import edu.fudan.backend.service.TopicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Slf4j
public class TopicController {
    @Autowired
    private TopicService topicService;

    @RequestMapping(value = "/topic", method = RequestMethod.GET)
    public Map<String, Object> getTopic(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            String urlPath = request.getScheme() + "://" +
                    request.getServerName() + ":"
                    + request.getServerPort()
                    + "/static/image/";
            List<Map<String, Object>> list = topicService.getAllTopic(urlPath);
            result.put("status", "ok");
            result.put("data", list);
        } catch (Exception e) {
            result.put("status", "error");
            log.error("get topic error");
        }
        return result;
    }

    @RequestMapping(value = "/topic", method = RequestMethod.POST)
    public Map<String, Object> getTopic(@RequestBody Map<String, Object> body) {
        Map<String, Object> result = new HashMap<>();
        try {
            String name = (String) body.get("name");
            String description = (String) body.get("desc");
            topicService.saveTopic(name, description);
            result.put("status", "ok");
        } catch (Exception e) {
            result.put("status", "error");
            log.error("get topic error");
        }
        return result;
    }

    @RequestMapping(value = "/image", method = RequestMethod.GET)
    public Map<String, Object> getImage(@RequestParam("topic") String topic) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Image> list = topicService.getAllImage(topic);
            result.put("status", "ok");
            result.put("data", list);
        } catch (Exception e) {
            result.put("status", "error");
            log.error("get topic error");
        }
        return result;
    }
}
