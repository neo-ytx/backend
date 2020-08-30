package edu.fudan.backend.service;

import edu.fudan.backend.model.Document;
import edu.fudan.backend.model.EsDocument;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface ElasticsearchService {
    void createIndexFromDB(Document document, File file) throws Exception;

    void createIndex(EsDocument esDocument) throws Exception;

    List<Map<String, Object>> search(String keyword) throws Exception;
}
