package edu.fudan.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
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
@AllArgsConstructor
@Accessors(chain = true)
@Document(indexName = "document", shards = 5, replicas = 1)
public class EsDocument {
    @Id
    private Long id;
    @Field(type = FieldType.Keyword)
    private String name;
    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd'T'hh:mm:ss.SSS'Z'")
    private Date createTime;
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String content;
}
