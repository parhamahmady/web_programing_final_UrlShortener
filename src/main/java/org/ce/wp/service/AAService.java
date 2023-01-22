package org.ce.wp.service;

import org.ce.wp.dto.GenerateTokenRequestDto;
import org.ce.wp.dto.SignUpRequestDto;
import org.ce.wp.dto.SignUpResponseDto;
import org.ce.wp.exception.CredentialsException;
import org.ce.wp.exception.InvaildJwtToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

/**
 * @author Parham Ahmadi
 * @since 20.01.23
 */
public interface AAService {

    String generateToken(String username);

    String generateToken(GenerateTokenRequestDto requestDto) throws CredentialsException;

    Optional<String> getUsernameByJwt(String token);

    UsernamePasswordAuthenticationToken authenticateJwt(String token) throws InvaildJwtToken;

    SignUpResponseDto signUpUser(SignUpRequestDto requestDto);
}
