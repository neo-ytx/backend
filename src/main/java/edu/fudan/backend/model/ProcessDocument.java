package edu.fudan.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Integer documentId;

    @Column
    private String name;
    /**
     * active, exception, normal, finish
     */
    @Column
    private String status;

    @Column
    private Date createTime;

    @Column
    private Date updateTime;

    @Column
    private String Owner;

    @Column
    private Integer percent;

    @Column
    private String description;
}
