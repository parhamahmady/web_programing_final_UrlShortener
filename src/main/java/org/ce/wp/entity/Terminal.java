package org.ce.wp.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Parham Ahmadi
 * @since 20.01.23
 */
@Data
@Entity
@Table(name = "TERMINAL")
public class Terminal {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "STATUS_CODE")
    private Integer statusCode;

    @Column(name = "FAILURE_REASON")
    private String failureReason;

    @ManyToOne
    @JoinColumn(name = "URL_ID")
    private Url url;
}
