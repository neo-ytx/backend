package edu.fudan.backend.service;

import edu.fudan.backend.model.Image;

import java.util.List;
import java.util.Map;

public interface TopicService {
    List<Map<String, Object>> getAllTopic(String urlPath) throws Exception;

    void saveTopic(String name, String description) throws Exception;

    List<Image> getAllImage(String topicName) throws Exception;
}
