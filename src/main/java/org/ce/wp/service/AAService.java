package org.ce.wp.service;

import org.ce.wp.dto.LoginRequestDto;
import org.ce.wp.dto.SignUpRequestDto;
import org.ce.wp.dto.SignUpResponseDto;
import org.ce.wp.exception.CredentialsException;
import org.ce.wp.exception.InvalidJwtToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Optional;

/**
 * @author Parham Ahmadi
 * @since 27.06.23
 */
public interface AAService {

    String generateToken(String username);

    String login(LoginRequestDto requestDto) throws CredentialsException;

    Optional<String> getUsernameByJwt(String token);

    UsernamePasswordAuthenticationToken authenticateJwt(String token) throws InvalidJwtToken;

    SignUpResponseDto signUpUser(SignUpRequestDto requestDto) throws CredentialsException;

    void activate(String id);
}
