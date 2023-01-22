package org.ce.wp.repository;

import org.ce.wp.entity.Url;
import org.ce.wp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Parham Ahmadi
 * @since 20.01.23
 */
public interface UrlRepository extends JpaRepository<Url, Long> {

    List<Url> findByUser(User user);

}
