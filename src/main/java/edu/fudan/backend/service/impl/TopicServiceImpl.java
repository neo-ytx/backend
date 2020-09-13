package edu.fudan.backend.service.impl;

import edu.fudan.backend.dao.ImageRepository;
import edu.fudan.backend.dao.TopicRepository;
import edu.fudan.backend.model.Image;
import edu.fudan.backend.model.Topic;
import edu.fudan.backend.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private ImageRepository imageRepository;

    @Override
    public List<Topic> getAllTopic() throws Exception {
        List<Topic> list = topicRepository.findAll();
        return list;
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
