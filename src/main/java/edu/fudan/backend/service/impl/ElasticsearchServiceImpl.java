package edu.fudan.backend.service.impl;

import edu.fudan.backend.dao.EsDocumentRepository;
import edu.fudan.backend.model.Document;
import edu.fudan.backend.model.EsDocument;
import edu.fudan.backend.service.ElasticsearchService;
import edu.fudan.backend.utils.POIUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ElasticsearchServiceImpl implements ElasticsearchService {

    @Autowired
    private EsDocumentRepository esDocumentRepository;

    @Autowired
    private POIUtils poiUtils;

    @Async
    @Override
    public void createIndexFromDB(Document document, MultipartFile file) throws Exception {
        String content = poiUtils.document2String(file);
        EsDocument esDocument = new EsDocument();
        esDocument.setContent(content);
        esDocument.setDocumentId((long) document.getId());
        esDocument.setName(file.getName());
        this.createIndex(esDocument);
    }

    @Override
    public void createIndex(EsDocument esDocument) throws Exception {
        esDocumentRepository.save(esDocument);
    }

    @Override
    public List<Map<String, Object>> search(String keyword) throws Exception {
        List<Map<String, Object>> result = new ArrayList<>();
        List<SearchHit<EsDocument>> searchHitsList = esDocumentRepository.findByNameOrContent(keyword, keyword);
        for (SearchHit<EsDocument> searchHit : searchHitsList) {
            Map<String, Object> objectMap = new HashMap<>();
            Map<String, List<String>> map = searchHit.getHighlightFields();
            ;
            List<String> list = map.get("content");
            StringBuilder stringBuilder = new StringBuilder();
            for (String content : list) {
                stringBuilder.append(content).append("......");
            }
            objectMap.put("name", searchHit.getContent().getName());
            objectMap.put("content", stringBuilder.toString());
            result.add(objectMap);
        }
        return result;
    }
}
