package edu.fudan.backend.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @Author tyuan@ea.com
 * @Date 8/11/2020 2:42 PM
 */
@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String nickname;

    @Column
    String role;

    public User() {
    }
}
