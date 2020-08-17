package edu.fudan.backend.dao;

import edu.fudan.backend.model.EsDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @Author tyuan@ea.com
 * @Date 8/17/2020 3:30 PM
 */
public interface EsDocumentRepository extends ElasticsearchRepository<EsDocument, Long> {
}
