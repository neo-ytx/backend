package edu.fudan.backend.service.impl;

import edu.fudan.backend.dao.DocumentRepository;
import edu.fudan.backend.model.Document;
import edu.fudan.backend.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @Author tyuan@ea.com
 * @Date 8/14/2020 11:23 AM
 */
@Service
public class DocumentServiceImpl implements DocumentService {
    @Autowired
    private DocumentRepository documentRepository;

    @Override
    public Page<Document> getAllDocument(String userId, String name, Pageable pageable) {
        Page<Document> list;
        if (name == null) {
            list = documentRepository.findAllByUserId(userId, pageable);
        } else {
            list = documentRepository.findAllByUserIdAndNameLike(userId, name + "*", pageable);
        }
        return list;
    }

    @Override
    public Page<Document> getAllDocument(Integer current, Integer pageSize, String userId) {
        Pageable pageable = PageRequest.of(2, 20);
        return this.getAllDocument(userId, null, pageable);
    }
}
