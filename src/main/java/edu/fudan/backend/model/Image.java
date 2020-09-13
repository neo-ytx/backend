package edu.fudan.backend.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Integer topicId;

    @Column
    private String location;

    @Column
    private String filename;
}
