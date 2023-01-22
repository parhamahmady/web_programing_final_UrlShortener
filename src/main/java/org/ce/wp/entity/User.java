package org.ce.wp.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Parham Ahmadi
 * @since 20.01.23
 */
@Data
@Entity
@Table(name = "USER_CLIENT")
public class User {

    @Id
    @Column(name = "ID", unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "USERNAME", nullable = false)
    private String username;

    @Column(name = "PASSWORD", nullable = false)
    private String password;
}
