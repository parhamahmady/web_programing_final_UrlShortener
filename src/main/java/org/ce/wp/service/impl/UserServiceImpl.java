package org.ce.wp.service.impl;

import lombok.RequiredArgsConstructor;
import org.ce.wp.dto.SignUpRequestDto;
import org.ce.wp.dto.SignUpResponseDto;
import org.ce.wp.entity.User;
import org.ce.wp.exception.AlertEngineInternalException;
import org.ce.wp.repository.UserRepository;
import org.ce.wp.service.AAService;
import org.ce.wp.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

/**
 * @author Parham Ahmadi
 * @since 20.01.23
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
    public boolean signUpUser(SignUpRequestDto requestDto) {
        User user = new User();
        user.setUsername(requestDto.username());
        user.setPassword(requestDto.password());
        user = userRepository.save(user);
        if (Objects.isNull(user.getId())) {
            throw new AlertEngineInternalException("Cant Save User");
        }
        return true;
    }
}
