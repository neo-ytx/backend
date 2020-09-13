package edu.fudan.backend.service.impl;

import edu.fudan.backend.client.GraphClient;
import edu.fudan.backend.dao.ImageRepository;
import edu.fudan.backend.dao.TopicRepository;
import edu.fudan.backend.model.Image;
import edu.fudan.backend.model.Topic;
import edu.fudan.backend.service.GraphService;
import edu.fudan.backend.utils.FileUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class GraphServiceImpl implements GraphService {
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Value("${file.uploadFolder}")
    private String fileRoot;

    @Autowired
    private GraphClient graphClient;

    @Override
    public void saveGraphImg(String content, String topic) throws Exception {
        String filename = UUID.randomUUID().toString() + ".png";
        String path = fileRoot + "static/image/";
        FileUtils.saveBase64Img(content, path, filename);
        List<Topic> list = topicRepository.findAllByName(topic);
        if (list.size() > 0) {
            Integer id = list.get(0).getId();
            Image image = new Image();
            image.setFilename(filename);
            image.setLocation(path + filename);
            image.setTopicId(id);
            imageRepository.save(image);
        }
    }

    @Override
    public Map<String, Object> search(String keyword) throws Exception {
        String info = graphClient.getInformation();
        log.info(info);
        Map<String, Object> request = new HashMap<>();
        Map<String, Object> statements = new HashMap<>();

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("param", ".*" + keyword + ".*");

        statements.put("statement", "MATCH (n)-[r]-(m) WHERE n.name =~ $param RETURN n,m,r");
        statements.put("parameters", parameters);

        List<Map<String, Object>> list = new ArrayList<>();
        list.add(statements);

        request.put("statements", list);
        Map<String, Object> response = graphClient.search(request);
        List<Map<String, Object>> results = (List<Map<String, Object>>) response.get("results");
        List<Map<String, Object>> items = null;
        Map<Integer, Node> nodes = new HashMap<>();
        List<Map<String, Object>> links = new ArrayList<>();
        Map<String, Integer> obj2id = new HashMap<>();
        if (results.size() > 0) {
            Map<String, Object> result = results.get(0);
            items = (List<Map<String, Object>>) result.get("data");
        }
        for (Map<String, Object> item : items) {
            List<Map<String, Object>> rows = (List<Map<String, Object>>) item.get("row");

            String fromName = (String) rows.get(0).get("name");
            Integer from = getIdFromObj(fromName, obj2id);
            String toName = (String) rows.get(1).get("name");
            Integer to = getIdFromObj(toName, obj2id);
            String rel = (String) rows.get(2).get("name");

            Node source = new Node();
            source.name = fromName;
            source.id = from;
            source.category = 0;
            nodes.put(from, source);

            Node target = new Node();
            target.name = toName;
            target.id = to;
            target.category = 1;
            nodes.put(to, target);

            Map<String, Object> link = new HashMap<>();
            link.put("name", rel);
            link.put("source", from);
            link.put("target", to);
            links.add(link);
        }
        List<Node> list1 = new ArrayList<>(nodes.values());
        list1.sort(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return o1.id - o2.id;
            }
        });

        int cnt = 0;
        for (Map<String, Object> link : links) {
            link.put("id", cnt++);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("nodes", list1);
        result.put("links", links);
        log.info("response,{}", result);
        return result;
    }

    private Integer getIdFromObj(String name, Map<String, Integer> obj2id) {
        if (obj2id.containsKey(name)) {
            return obj2id.get(name);
        } else {
            int id = obj2id.size();
            obj2id.put(name, id);
            return id;
        }
    }

    @Data
    public static class Node {
        public int id;
        public String name;
        public Integer category;
    }

}
