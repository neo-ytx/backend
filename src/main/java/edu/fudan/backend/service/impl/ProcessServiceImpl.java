package edu.fudan.backend.service.impl;

import edu.fudan.backend.dao.DocumentRepository;
import edu.fudan.backend.dao.ProcessDocumentRepository;
import edu.fudan.backend.model.Document;
import edu.fudan.backend.model.ProcessDocument;
import edu.fudan.backend.service.ProcessService;
import edu.fudan.backend.service.PythonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j

public class ProcessServiceImpl implements ProcessService {
    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private ProcessDocumentRepository processDocumentRepository;

    @Autowired
    private PythonService pythonService;

    @Value("${file.uploadFolder}")
    private String fileRoot;

    @Override
    public List<Map<String, Object>> getAllProcess() throws Exception {
        List<ProcessDocument> list = processDocumentRepository.findAll();
        List<Map<String, Object>> result = new ArrayList<>();
        for (ProcessDocument processDocument : list) {
            Map<String, Object> obj = new HashMap<>();
            obj.put("key", processDocument.getId());
            obj.put("title", processDocument.getName());
            obj.put("description", processDocument.getDescription());
            obj.put("createdAt", processDocument.getCreateTime());
            obj.put("percent", processDocument.getPercent());
            obj.put("owner", processDocument.getOwner());
            obj.put("status", processDocument.getStatus());
            result.add(obj);
        }
        return result;
    }

    @Override
    public void createProcess(Integer documentId) throws Exception {
        Optional<Document> optionalDocument = documentRepository.findById(documentId);
        if (optionalDocument.isPresent()) {
            Document document = optionalDocument.get();
            document.setStatus(2);
            ProcessDocument processDocument = new ProcessDocument();
            processDocument.setDocumentId(documentId);
            processDocument.setStatus("Wait");
            processDocument.setCreateTime(new Date());
            processDocument.setDescription(document.getDescription());
            processDocument.setName(document.getName());
            processDocument.setOwner(document.getUsername());
            processDocument.setUpdateTime(processDocument.getCreateTime());
            processDocument.setPercent(0);
            processDocument.setStatus("normal");
            documentRepository.save(document);
            processDocumentRepository.save(processDocument);
        } else {
            log.error("can not find document in database");
            throw new RuntimeException("数据库找不到文件");
        }
    }

    @Override
    public void createProcess(List<Integer> documentIdList) throws Exception {
        for (Integer documentId : documentIdList) {
            this.createProcess(documentId);
        }
    }

    @Override
    public void startProcess() throws Exception {
        List<ProcessDocument> processDocuments = processDocumentRepository.findAllByStatus("active");
        if (processDocuments.size() > 0) {
            log.warn("there is a process running.");
            return;
        }
        processDocuments = processDocumentRepository.findAllByStatus("normal");
        if (processDocuments.size() > 0) {
            ProcessDocument processDocument = processDocuments.get(0);
            Document document = documentRepository.getOne(processDocument.getDocumentId());
            String filenameOrigin = document.getName();
            String type = filenameOrigin.substring(filenameOrigin.lastIndexOf("."));
            String filename = fileRoot + "/static" + document.getUsername() + "/" + document.getUuid() + type;
            pythonService.createJob(processDocument.getId(), filename);
            processDocument.setStatus("active");
            processDocument.setPercent((int) (Math.random() * 100));
            processDocumentRepository.save(processDocument);
        } else {
            log.warn("there are no processes.");
        }
    }

    @Override
    public Boolean startProcess(Integer processId) throws Exception {
        List<ProcessDocument> processDocuments = processDocumentRepository.findAllByStatus("active");
        if (processDocuments.size() > 0) {
            log.warn("there is a process running.");
            return false;
        }
        Optional<ProcessDocument> optional = processDocumentRepository.findById(processId);
        if (optional.isPresent()) {
            ProcessDocument processDocument = optional.get();
            processDocument.setStatus("active");
            processDocumentRepository.save(processDocument);
            return true;
        } else {
            log.error("Can not find process");
            return false;
        }
    }



    @Override
    public String getProcessTime() throws Exception {
        List<ProcessDocument> list = processDocumentRepository.findAllByStatus("finish");
        long allTime = 0;
        for (ProcessDocument processDocument : list) {
            allTime += processDocument.getUpdateTime().getTime() - processDocument.getCreateTime().getTime();
        }
        long time = (list.size() != 0 ? allTime / list.size() : 0) / 60;
        return String.valueOf(time);
    }

    @Override
    public Integer getProcessFinish() throws Exception {
        return processDocumentRepository.findAllByStatus("finish").size();
    }

    @Override
    public void updateProcess(Integer processId, Integer percent, String status, String filename) throws Exception {
        ProcessDocument processDocument = processDocumentRepository.getOne(processId);
        processDocument.setUpdateTime(new Date());
        processDocument.setPercent(percent);
        if (status != null && status.equals("active")) {
            processDocument.setStatus(status);
        } else if (status != null && status.equals("finish")) {
            processDocument.setStatus(status);
            Document document = documentRepository.getOne(processDocument.getDocumentId());
            if (document != null) {
                document.setStatus(0);
                document.setJsonPos(filename);
                documentRepository.save(document);
            } else {
                log.error("document {} does not exist", processDocument.getDocumentId());
            }
        }
        processDocumentRepository.save(processDocument);

    }

}
