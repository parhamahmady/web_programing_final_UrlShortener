package org.ce.wp.service.impl;

import lombok.RequiredArgsConstructor;
import org.ce.wp.dto.CreateUrlRequestDto;
import org.ce.wp.dto.CreateUrlResponseDto;
import org.ce.wp.dto.FindUrlResponseDto;
import org.ce.wp.dto.UrlReportResponseDto;
import org.ce.wp.entity.Terminal;
import org.ce.wp.entity.Url;
import org.ce.wp.entity.User;
import org.ce.wp.exception.CredentialsException;
import org.ce.wp.exception.InvalidUrlIdException;
import org.ce.wp.repository.TerminalRepository;
import org.ce.wp.repository.UrlRepository;
import org.ce.wp.service.EndpointService;
import org.ce.wp.service.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Parham Ahmadi
 * @since 22.01.23
 */
@Service
@RequiredArgsConstructor
public class EndpointServiceImpl implements EndpointService {
    private final UserService userService;
    private final UrlRepository urlRepository;
    private final TerminalRepository terminalRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public CreateUrlResponseDto creatEndpoint(CreateUrlRequestDto requestDto, String username) {
        User user = getUser(username);
        Url url = new Url();
        url.setUser(user);
        url.setUri(requestDto.uri());
        url.setThreshold(requestDto.threshold());
        url = urlRepository.save(url);
        if (Objects.isNull(url.getId())) {
            return new CreateUrlResponseDto(false);
        }
        return new CreateUrlResponseDto(true);
    }

    @Override
    @Transactional(readOnly = true)
    public FindUrlResponseDto findEndPoint(String username) {
        User user = getUser(username);
        List<Url> urls = urlRepository.findByUser(user);
        return new FindUrlResponseDto(urls);
    }

    @Override
    @Transactional(readOnly = true)
    public UrlReportResponseDto reportUrl(String urlId, String username) throws InvalidUrlIdException, CredentialsException {
        Optional<Url> urlOptional = urlRepository.findById(Long.parseLong(urlId));
        if (urlOptional.isEmpty()) {
            throw new InvalidUrlIdException("Invalid Url Id:" + urlId);
        }
        if (!urlOptional.get().getUser().getUsername().equals(username)) {
            throw new CredentialsException("UrlId :" + urlId + "not belong to username: " + username);
        }
        List<Terminal> requests = terminalRepository.findAllByUrl(urlOptional.get());
        AtomicInteger successful = new AtomicInteger();
        AtomicInteger unsuccessful = new AtomicInteger();
        requests.forEach(terminal -> {
            if (Objects.isNull(terminal.getStatusCode()) || terminal.getStatusCode() % 100 == 2) {
                successful.getAndIncrement();
            } else {
                unsuccessful.getAndIncrement();
            }
        });
        return new UrlReportResponseDto(successful.get(), unsuccessful.get(), urlOptional.get());
    }

    private User getUser(String username) {
        Optional<User> user = userService.getUser(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Username not found, username: " + username);
        }
        return user.get();
    }
}
