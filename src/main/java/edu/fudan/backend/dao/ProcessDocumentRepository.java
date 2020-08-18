package edu.fudan.backend.dao;

import edu.fudan.backend.model.ProcessDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProcessDocumentRepository extends JpaRepository<ProcessDocument, Integer> {
    List<ProcessDocument> findAllByStatus(String status);

    List<ProcessDocument> findAllByDocumentId(Integer documentId);
}
