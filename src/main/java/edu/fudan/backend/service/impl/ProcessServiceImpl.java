package edu.fudan.backend.service.impl;

import edu.fudan.backend.dao.DocumentRepository;
import edu.fudan.backend.dao.ProcessDocumentRepository;
import edu.fudan.backend.model.Document;
import edu.fudan.backend.model.ProcessDocument;
import edu.fudan.backend.service.ProcessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProcessServiceImpl implements ProcessService {
    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private ProcessDocumentRepository processDocumentRepository;

    @Override
    public void createProcess(Integer documentId) throws Exception {
        Optional<Document> optionalDocument = documentRepository.findById(documentId);
        if (optionalDocument.isPresent()) {
            Document document = optionalDocument.get();
            document.setStatus(2);
            ProcessDocument processDocument = new ProcessDocument();
            processDocument.setDocumentId(documentId);
            processDocument.setStatus("Wait");
            documentRepository.save(document);
            processDocumentRepository.save(processDocument);
        } else {
            log.error("can not find document in database");
            throw new RuntimeException("数据库找不到文件");
        }
    }

    @Override
    public void startProcess() throws Exception {
        List<ProcessDocument> processDocuments = processDocumentRepository.findAllByStatus("Run");
        if (processDocuments.size() > 0) {
            log.warn("there is a process running.");
            return;
        }
        processDocuments = processDocumentRepository.findAllByStatus("Wait");
        if (processDocuments.size() > 0) {
            ProcessDocument processDocument = processDocuments.get(0);
            // TODO: 2020/8/19 run python job
            processDocument.setStatus("Start");
            processDocumentRepository.save(processDocument);
        } else {
            log.warn("there are no processes.");
        }
    }

    @Override
    public Boolean startProcess(Integer processId) throws Exception {
        List<ProcessDocument> processDocuments = processDocumentRepository.findAllByStatus("Run");
        if (processDocuments.size() > 0) {
            log.warn("there is a process running.");
            return false;
        }
        Optional<ProcessDocument> optional = processDocumentRepository.findById(processId);
        if (optional.isPresent()) {
            ProcessDocument processDocument = optional.get();
            processDocument.setStatus("Run");
            processDocumentRepository.save(processDocument);
            return true;
        } else {
            log.error("Can not find process");
            return false;
        }
    }

    @Override
    public void finishProcess(Integer documentId) throws Exception {

    }

}
