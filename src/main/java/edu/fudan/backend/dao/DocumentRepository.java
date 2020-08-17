package edu.fudan.backend.dao;

import edu.fudan.backend.model.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author tyuan@ea.com
 * @Date 8/11/2020 3:35 PM
 */
public interface DocumentRepository extends JpaRepository<Document, Integer> {

    Page<Document> findAllByUsername(String userId, Pageable pageable);

    Page<Document> findAllByUsernameAndNameLike(String userId, String name, Pageable pageable);
}
