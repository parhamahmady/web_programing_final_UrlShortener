package org.ce.wp.service.impl;

import lombok.RequiredArgsConstructor;
import org.ce.wp.dto.SignUpRequestDto;
import org.ce.wp.entity.User;
import org.ce.wp.exception.CredentialsException;
import org.ce.wp.exception.ShortenerInternalException;
import org.ce.wp.repository.UserRepository;
import org.ce.wp.service.UserService;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

/**
 * @author Parham Ahmadi
 * @since 27.06.23
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUser(String username) {
        User user = userRepository.findByUsername(username);
        return Objects.nonNull(user) ? Optional.of(user) : Optional.empty();
    }

    @Override
    public User signUpUser(SignUpRequestDto requestDto) throws CredentialsException {
        User username = userRepository.findByUsername(requestDto.username());
        if (Objects.nonNull(username)) {
            throw new CredentialsException("Duplicate User");
        }
        try {
            User user = new User();
            user.setUsername(requestDto.username());
            user.setPassword(requestDto.password());
            user.setEmail(requestDto.email());
            user = userRepository.save(user);
            if (Objects.isNull(user.getId())) {
                throw new ShortenerInternalException("Cant Save User");
            }
            return user;
        } catch (ConstraintViolationException e) {
            throw new CredentialsException("Duplicate User");
        }
    }

    @Override
    public void activate(User user) {
        user.setActive(true);
        userRepository.save(user);
    }
}
