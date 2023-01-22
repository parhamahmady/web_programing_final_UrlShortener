package org.ce.wp.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Parham Ahmadi
 * @since 20.01.23
 */
@Data
@Entity
@Table(name = "URL")
public class Url {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID", unique = true)
    private User user;

    @Column(name = "URI", nullable = false)
    private String uri;

    @Column(name = "THRESHOLD", nullable = false)
    private Integer threshold;
}
