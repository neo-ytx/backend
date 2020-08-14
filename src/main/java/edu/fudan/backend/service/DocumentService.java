package edu.fudan.backend.service;

import edu.fudan.backend.model.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @Author tyuan@ea.com
 * @Date 8/14/2020 11:23 AM
 */
public interface DocumentService {
    Page<Document> getAllDocument(String userId, String name, Pageable pageable);

    Page<Document> getAllDocument(Integer current, Integer pageSize, String userId);
}
