package edu.fudan.backend.service;

import edu.fudan.backend.model.EsDocument;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @Author tyuan@ea.com
 * @Date 8/26/2020 5:25 PM
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class ElasticsearchServiceTest {

    @Autowired
    private ElasticsearchService elasticsearchService;

    @Test
    void createIndex() throws Exception {
        EsDocument a = new EsDocument(null, (long) 10, "123.docx", "中华人民共和国", "ytx", new Date());
        elasticsearchService.createIndex(a);
    }

    @Test
    void search() throws Exception {
        elasticsearchService.search("中华");
    }
}