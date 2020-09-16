package edu.fudan.backend.service.impl;

import edu.fudan.backend.dao.ImageRepository;
import edu.fudan.backend.dao.TopicRepository;
import edu.fudan.backend.model.Image;
import edu.fudan.backend.model.Topic;
import edu.fudan.backend.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TopicServiceImpl implements TopicService {
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private ImageRepository imageRepository;

    @Override
    public List<Map<String, Object>> getAllTopic(String urlPath) throws Exception {
        List<Topic> list = topicRepository.findAll();
        List<Map<String, Object>> result = new ArrayList<>();
        for (Topic topic : list) {
            Map<String, Object> obj = new HashMap<>();
            obj.put("id", topic.getId());
            obj.put("title", topic.getName());
            obj.put("description", topic.getDescription());
            List<Image> imageList = imageRepository.findAllByTopicId(topic.getId());
            List<String> topicList = new ArrayList<>();
            for (Image image : imageList) topicList.add(urlPath + image.getFilename());
            obj.put("images", topicList);
            result.add(obj);
        }
        return result;
    }

    @Override
    public void saveTopic(String name, String description) throws Exception {
        Topic topic = new Topic();
        topic.setName(name);
        topic.setDescription(description);
        topicRepository.save(topic);
    }

    @Override
    public List<Image> getAllImage(String topicName) throws Exception {
        List<Topic> list = topicRepository.findAllByName(topicName);
        if (list.size() > 0) {
            Integer id = list.get(0).getId();
            List<Image> imageList = imageRepository.findAllByTopicId(id);
            return imageList;
        }
        return new ArrayList<>();
    }
}
