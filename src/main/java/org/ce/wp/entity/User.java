package org.ce.wp.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Parham Ahmadi
 * @since 27.06.23
 */
@Data
@Entity
@Table(name = "USER_CLIENT")
@SequenceGenerator(name = "user_seq_gen", sequenceName = "USER_SEQ", allocationSize = 1)
public class User {

    @Id
    @Column(name = "ID", unique = true)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq_gen")
    private Long id;

    @Column(name = "USERNAME", nullable = false)
    private String username;

    @Column(name = "PASSWORD", nullable = false)
    private String password;
}
