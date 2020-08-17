package edu.fudan.backend.service.impl;

import edu.fudan.backend.dao.DocumentRepository;
import edu.fudan.backend.dao.EsDocumentRepository;
import edu.fudan.backend.model.Document;
import edu.fudan.backend.model.EsDocument;
import edu.fudan.backend.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    public Page<Document> getAllDocument(Integer current, Integer pageSize, String username) throws Exception {
        Pageable pageable = PageRequest.of(2, 20);
        return this.getAllDocument(username, null, pageable);
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
        documentRepository.save(document);
        String type = file.getContentType();
        InputStream inputStream = file.getInputStream();
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        EsDocument esDocument = new EsDocument();
        esDocument.setContent(result.toString());
        esDocument.setCreateTime(date);
        esDocument.setName(file.getName());
        esDocumentRepository.save(esDocument);
    }
}
