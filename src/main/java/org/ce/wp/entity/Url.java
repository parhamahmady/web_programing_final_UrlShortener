package org.ce.wp.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Parham Ahmadi
 * @since 27.06.23
 */
@Data
@Entity
@Table(name = "URL")
@SequenceGenerator(name = "url_seq_gen", sequenceName = "URL_SEQ", allocationSize = 1)
public class Url {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "url_seq_gen")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID", unique = true)
    private User user;

    @Column(name = "URI", nullable = false)
    private String uri;

    @Column(name = "COUNT", nullable = false)
    private Integer count;
}
