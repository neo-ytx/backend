package edu.fudan.backend.service.impl;

import edu.fudan.backend.dao.DocumentRepository;
import edu.fudan.backend.dao.EsDocumentRepository;
import edu.fudan.backend.model.Document;
import edu.fudan.backend.service.DocumentService;
import edu.fudan.backend.service.ElasticsearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author tyuan@ea.com
 * @Date 8/14/2020 11:23 AM
 */
@Service
public class DocumentServiceImpl implements DocumentService {
    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private EsDocumentRepository esDocumentRepository;

    @Autowired
    private ElasticsearchService elasticsearchService;

    private static ThreadLocal<SimpleDateFormat> dateFormatThreadLocal = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss"));

    @Override
    public Page<Document> getAllDocument(String username, String name, Pageable pageable) throws Exception {
        Page<Document> list;
        if (name == null) {
            list = documentRepository.findAllByUsername(username, pageable);

        } else {
            list = documentRepository.findAllByUsernameAndNameLike(username, name + "*", pageable);
        }
        return list;
    }

    @Override
    public Map<String, Object> getAllDocument(Integer current, Integer pageSize, String username) throws Exception {
        Pageable pageable = PageRequest.of(current - 1, pageSize);
        Page<Document> documentPage = this.getAllDocument(username, null, pageable);
        Map<String, Object> result = new HashMap<>();
        result.put("list", documentObjectToMap(documentPage.getContent()));
        result.put("total", documentPage.getTotalElements());
        return result;
    }

    @Override
    public void uploadFile(MultipartFile file, String username) throws Exception {
        Date date = new Date();
        Document document = new Document();
        document.setCreatedTime(date);
        document.setUpdatedTime(date);
        document.setName(file.getOriginalFilename());
        document.setDescription("用户" + username + "在" + dateFormatThreadLocal.get().format(date) + "创建文件:" + file.getName());
        document.setEntityNum(0);
        document.setRelationNum(0);
        document.setStatus(1);
        document.setUsername(username);
        document = documentRepository.save(document);
        elasticsearchService.createIndexFromDB(document, file);
    }

    @Override
    public void deleteFiles(List<Integer> keyList) throws Exception {
        for (Integer key : keyList) {
            this.deleteFile(key);
        }
    }

    @Override
    public void deleteFile(Integer key) throws Exception {
        documentRepository.deleteById(key);
    }

    private List<Map<String, Object>> documentObjectToMap(List<Document> documentList) {
        List<Map<String, Object>> data = new ArrayList<>();
        for (Document document : documentList) {
            Map<String, Object> doc = new HashMap<>();
            doc.put("key", document.getId());
            doc.put("desc", document.getDescription());
            doc.put("name", document.getName());
            doc.put("owner", document.getUsername());
            doc.put("entityNo", document.getEntityNum());
            doc.put("ralNo", document.getRelationNum());
            doc.put("createdAt", document.getCreatedTime());
            doc.put("updatedTime", document.getUpdatedTime());
            doc.put("status", document.getStatus());
            data.add(doc);
        }
        return data;
    }
}
