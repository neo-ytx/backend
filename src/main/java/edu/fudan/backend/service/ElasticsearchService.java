package edu.fudan.backend.service;

import edu.fudan.backend.model.Document;
import edu.fudan.backend.model.EsDocument;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ElasticsearchService {
    void createIndexFromDB(Document document, MultipartFile file) throws Exception;

    void createIndex(EsDocument esDocument) throws Exception;

    List<Map<String, Object>> search(String keyword) throws Exception;
}
