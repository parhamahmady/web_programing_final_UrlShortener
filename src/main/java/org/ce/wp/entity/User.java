package org.ce.wp.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

/**
 * @author Parham Ahmadi
 * @since 27.06.23
 */
@Data
@Entity
@Table(name = "USER_CLIENT")
@SequenceGenerator(name = "user_seq_gen", sequenceName = "USER_SEQ", allocationSize = 1)
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 2880600761981601838L;

    @Id
    @Column(name = "ID", unique = true)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq_gen")
    private Long id;

    @Column(name = "USERNAME", nullable = false)
    private String username;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "ACTIVE")
    private boolean active;
}
