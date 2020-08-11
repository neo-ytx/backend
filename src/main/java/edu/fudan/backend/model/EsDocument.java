package edu.fudan.backend.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * @Author tyuan@ea.com
 * @Date 8/11/2020 3:25 PM
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@Document(indexName = "document", shards = 5, replicas = 1)
public class EsDocument {

    public EsDocument(Long id, String name, Date createTime, String content) {
        this.id = id;
        this.name = name;
        this.createTime = createTime;
        this.content = content;
    }

    @Id
    private Long id;
    @Field(type = FieldType.Keyword)
    private String name;
    @Field(type = FieldType.Date)
    private Date createTime;
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String content;
}
