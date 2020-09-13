package edu.fudan.backend.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

}
