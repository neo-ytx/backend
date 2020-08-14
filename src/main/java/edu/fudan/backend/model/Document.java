package edu.fudan.backend.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author tyuan@ea.com
 * @Date 8/11/2020 3:32 PM
 */
@Data
@Entity
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private Integer userId;
    @Column
    private Integer entityNum;
    @Column
    private Integer relationNum;
    @Column
    private Integer status;
    @Column
    private Date createdTime;
    @Column
    private Date updatedTime;

}
