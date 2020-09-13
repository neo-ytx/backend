package edu.fudan.backend.service;

import java.util.Map;

public interface GraphService {
    void saveGraphImg(String content, String topic) throws Exception;

    Map<String, Object> search(String keyword) throws Exception;
}
