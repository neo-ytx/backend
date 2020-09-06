package edu.fudan.backend.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Topic {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

}
