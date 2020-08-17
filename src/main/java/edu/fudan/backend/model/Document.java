package edu.fudan.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author tyuan@ea.com
 * @Date 8/11/2020 3:32 PM
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private String username;
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
    @Column
    private String jsonPos;

}
