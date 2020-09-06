package edu.fudan.backend.controller;

import edu.fudan.backend.model.Image;
import edu.fudan.backend.model.Topic;
import edu.fudan.backend.service.TopicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public Map<String, Object> getTopic() {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Topic> list = topicService.getAllTopic();
            result.put("status", "ok");
            result.put("data", list);
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
