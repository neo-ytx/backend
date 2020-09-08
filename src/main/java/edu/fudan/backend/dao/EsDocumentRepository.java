package edu.fudan.backend.dao;

import edu.fudan.backend.model.EsDocument;
import org.springframework.data.elasticsearch.annotations.Highlight;
import org.springframework.data.elasticsearch.annotations.HighlightField;
import org.springframework.data.elasticsearch.annotations.HighlightParameters;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @Author tyuan@ea.com
 * @Date 8/17/2020 3:30 PM
 */
public interface EsDocumentRepository extends ElasticsearchRepository<EsDocument, String> {
    @Highlight(fields = {
            @HighlightField(name = "name"),
            @HighlightField(name = "content")
    }, parameters = @HighlightParameters(preTags = "<em>", postTags = "</em>"))
    List<SearchHit<EsDocument>> findByNameOrContent(String name, String content);

    List<EsDocument> findAllByDocumentId(Long documentId);
}
