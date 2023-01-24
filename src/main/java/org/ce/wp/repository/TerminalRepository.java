package org.ce.wp.repository;

import org.ce.wp.entity.Terminal;
import org.ce.wp.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * @author Parham Ahmadi
 * @since 20.01.23
 */
public interface TerminalRepository extends JpaRepository<Terminal, Long> {

    List<Terminal> findAllByUrlAndRequestTimeAfter(Url url, Date requestTime);

    List<Terminal> findAllByUrl(Url url);
}
