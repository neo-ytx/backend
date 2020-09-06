package edu.fudan.backend.service.impl;

import edu.fudan.backend.dao.ImageRepository;
import edu.fudan.backend.dao.TopicRepository;
import edu.fudan.backend.model.Image;
import edu.fudan.backend.model.Topic;
import edu.fudan.backend.service.GraphService;
import edu.fudan.backend.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GraphServiceImpl implements GraphService {
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Value("${file.uploadFolder}")
    private String fileRoot;

    @Override
    public void saveGraphImg(String content, String topic) throws Exception {
        String filename = UUID.randomUUID().toString() + ".png";
        String path = fileRoot + "static/image/";
        FileUtils.saveBase64Img(content, path, filename);
        List<Topic> list = topicRepository.getAllByName(topic);
        if (list.size() > 0) {
            Long id = list.get(0).getId();
            Image image = new Image();
            image.setFilename(filename);
            image.setLocation(path + filename);
            image.setTopicId(id);
            imageRepository.save(image);
        }
    }


}
