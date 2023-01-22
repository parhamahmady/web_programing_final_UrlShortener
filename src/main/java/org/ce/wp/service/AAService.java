package org.ce.wp.service;

import org.ce.wp.dto.GenerateTokenRequestDto;
import org.ce.wp.dto.SignUpRequestDto;
import org.ce.wp.dto.SignUpResponseDto;
import org.ce.wp.exception.CredentialsException;
import org.ce.wp.exception.InvalidJwtToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Optional;

/**
 * @author Parham Ahmadi
 * @since 20.01.23
 */
public interface AAService {

    String generateToken(String username);

    String generateToken(GenerateTokenRequestDto requestDto) throws CredentialsException;

    Optional<String> getUsernameByJwt(String token);

    UsernamePasswordAuthenticationToken authenticateJwt(String token) throws InvalidJwtToken;

    SignUpResponseDto signUpUser(SignUpRequestDto requestDto);
}
