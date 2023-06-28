package org.ce.wp.service.impl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.ce.wp.dto.LoginRequestDto;
import org.ce.wp.dto.SignUpRequestDto;
import org.ce.wp.dto.SignUpResponseDto;
import org.ce.wp.entity.User;
import org.ce.wp.exception.CredentialsException;
import org.ce.wp.exception.InvalidJwtToken;
import org.ce.wp.service.AAService;
import org.ce.wp.service.MailService;
import org.ce.wp.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

/**
 * @author Parham Ahmadi
 * @since 27.06.23
 */
@Service
@RequiredArgsConstructor
public class AAServiceImpl implements AAService {
    private final UserService userService;
    private final MailService mailService;
    private static final byte[] KEY = TextCodec.BASE64.decode("Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=");

    @Setter(onMethod = @__(@Value("${token.validity.time.ms}")))
    private long tokenValidity;

    @Override
    public String generateToken(String username) {
        return Jwts.builder()
                .setClaims(new HashMap<>()).setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenValidity))
                .signWith(SignatureAlgorithm.HS256, KEY)
                .compact();
    }

    @Override
    public String login(LoginRequestDto requestDto) throws CredentialsException {
        Optional<User> user = userService.getUser(requestDto.username());
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User Not found with username: " + requestDto.username());
        }
        if (!user.get().isActive()) {
            throw new CredentialsException("User " + requestDto.username() + " Not Activated");
        }
        if (!user.get().getPassword().equals(requestDto.password())) {
            throw new CredentialsException("Invalid Password for user " + requestDto.username());
        }
        return generateToken(requestDto.username());
    }

    @Override
    public UsernamePasswordAuthenticationToken authenticateJwt(String token) throws InvalidJwtToken {
        Optional<String> username = getUsernameByJwt(token);
        if (username.isEmpty()) {
            throw new InvalidJwtToken("Invalid Token: " + token);
        }
        boolean isTokenExpired = isExpired(token);
        if (isTokenExpired) {
            throw new InvalidJwtToken("Expired Token: " + token);
        }
        Optional<User> user = userService.getUser(username.get());
        if (user.isEmpty()) {
            throw new InvalidJwtToken("Token: " + token + " ,is not belong to a user");
        }
        UserDetails userDetails = getUserDetails(user.get());
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public SignUpResponseDto signUpUser(SignUpRequestDto requestDto) throws CredentialsException {
        User result = userService.signUpUser(requestDto);
        mailService.sendMain(requestDto.email(), "Link: http://localhost:8081/shortener/user/reg/" + result.getUsername());
        String token = generateToken(requestDto.username());
        return new SignUpResponseDto(true, token);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void activate(String username) {
        Optional<User> user = userService.getUser(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User Not Found");
        }
        userService.activate(user.get());
    }

    private org.springframework.security.core.userdetails.User getUserDetails(User user) {
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }

    private boolean isExpired(String token) {
        return Jwts.parser().setSigningKey(KEY).parseClaimsJws(token).getBody().getExpiration().before(new Date());
    }

    public Optional<String> getUsernameByJwt(String token) {
        try {
            return Optional.of(Jwts.parser().setSigningKey(KEY).parseClaimsJws(token).getBody().getSubject());
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
