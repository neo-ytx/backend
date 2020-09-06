package edu.fudan.backend.service;

import edu.fudan.backend.model.Image;
import edu.fudan.backend.model.Topic;

import java.util.List;

public interface TopicService {
    List<Topic> getAllTopic() throws Exception;

    List<Image> getAllImage(String topicName) throws Exception;
}
