package org.ce.wp.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Parham Ahmadi
 * @since 27.06.23
 */
@Data
@Entity
@Table(name = "TERMINAL")
@SequenceGenerator(name = "terminal_seq_gen", sequenceName = "TERMINAL_SEQ", allocationSize = 1)
public class Terminal {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "terminal_seq_gen")
    private Long id;

    @Column(name = "STATUS_CODE")
    private Integer statusCode;

    @Column(name = "FAILURE_REASON")
    private String failureReason;

    @Column(name = "REQUEST_TIME")
    @Temporal(TemporalType.DATE)
    private Date requestTime;

    @ManyToOne
    @JoinColumn(name = "URL_ID")
    private Url url;
}
