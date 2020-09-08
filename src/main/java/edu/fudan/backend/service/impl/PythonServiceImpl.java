package edu.fudan.backend.service.impl;

import edu.fudan.backend.client.GoodClient;
import edu.fudan.backend.client.PythonClient;
import edu.fudan.backend.dao.DocumentRepository;
import edu.fudan.backend.dao.EsDocumentRepository;
import edu.fudan.backend.dao.ProcessDocumentRepository;
import edu.fudan.backend.model.Document;
import edu.fudan.backend.model.EsDocument;
import edu.fudan.backend.model.ProcessDocument;
import edu.fudan.backend.service.PythonService;
import edu.fudan.backend.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author tyuan@ea.com
 * @Date 8/11/2020 3:39 PM
 */
@Service
@Slf4j
public class PythonServiceImpl implements PythonService {
    @Autowired
    private PythonClient pythonClient;
    @Autowired
    private GoodClient goodClient;

    @Value("${file.uploadFolder}")
    private String fileRoot;

    @Autowired
    private ProcessDocumentRepository processDocumentRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private EsDocumentRepository esDocumentRepository;

    @Override
    public void createJob(Integer processId, String filename) throws Exception {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("process_id", processId);
        requestBody.put("filename", filename);
        pythonClient.createJob(requestBody);
    }

    @Override
    @Async
    public void createGoodJob(Integer processId, String filename) throws Exception {
        Optional<ProcessDocument> optional = processDocumentRepository.findById(processId);
        if (!optional.isPresent()) {
            log.error("Process Document is null");
            return;
        }
        ProcessDocument processDocument = optional.get();
        Integer documentId = processDocument.getDocumentId();
        List<EsDocument> list = esDocumentRepository.findAllByDocumentId((long) documentId);
        EsDocument esDocument = list.get(0);
        String content = esDocument.getContent();
        List<String> requestList = new ArrayList<>();
        int start = 0;
        while (start < content.length()) {
            int end = start + 1000;
            String split = content.substring(start, Math.min(end, content.length()));
            requestList.add(split);
            start = end;
        }
        Map<String, Object> body = new HashMap<>();
        List<Map<String, String>> result = new ArrayList<>();
        for (int i = 0; i < requestList.size(); i++) {
            String text = requestList.get(i);
            body.put("text", text);
            Map<String, Object> response = goodClient.getTriple(body);
            List<Object> items = null;
            if ((items = (List<Object>) response.get("items")) != null) {
                result.addAll((List<Map<String, String>>) items.get(0));
            }
            processDocument.setPercent((int) ((i / (double) requestList.size()) * 100));
            processDocumentRepository.save(processDocument);
            Thread.sleep(1000);
        }
        processDocument.setPercent(100);
        processDocument.setStatus("finish");
        processDocumentRepository.save(processDocument);
        Optional<Document> optionalDocument = documentRepository.findById(processDocument.getDocumentId());
        if (!optionalDocument.isPresent()) {
            log.error("document is null.");
            return;
        }
        Document document = optionalDocument.get();
        String path = fileRoot + "static/" + document.getUsername() + "/" + document.getUuid() + ".json";
        FileUtils.saveJson(result, path);
        // TODO: 2020/9/8 处理数据结果

    }
}
