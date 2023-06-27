package org.ce.wp.repository;

import org.ce.wp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Parham Ahmadi
 * @since 27.06.23
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
