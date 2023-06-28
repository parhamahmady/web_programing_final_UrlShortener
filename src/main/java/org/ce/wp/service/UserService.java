package org.ce.wp.service;

import org.ce.wp.dto.SignUpRequestDto;
import org.ce.wp.dto.SignUpResponseDto;
import org.ce.wp.entity.User;
import org.ce.wp.exception.CredentialsException;

import java.util.Optional;

/**
 * @author Parham Ahmadi
 * @since 27.06.23
 */
public interface UserService {

    Optional<User> getUser(String username);

    User signUpUser(SignUpRequestDto requestDto) throws CredentialsException;

    void activate(User user);
}
