package edu.fudan.backend.service;

import edu.fudan.backend.model.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author tyuan@ea.com
 * @Date 8/14/2020 11:23 AM
 */
public interface DocumentService {
    Page<Document> getAllDocument(String username, String name, Pageable pageable) throws Exception;

    Page<Document> getAllDocument(Integer current, Integer pageSize, String username) throws Exception;

    void uploadFile(MultipartFile file, String username) throws Exception;
}
