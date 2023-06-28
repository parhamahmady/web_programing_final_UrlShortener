package org.ce.wp.service.impl;

import lombok.RequiredArgsConstructor;
import org.ce.wp.dto.CreateUrlRequestDto;
import org.ce.wp.dto.CreateUrlResponseDto;
import org.ce.wp.entity.Url;
import org.ce.wp.entity.User;
import org.ce.wp.repository.UrlRepository;
import org.ce.wp.service.EndpointService;
import org.ce.wp.service.QRCodeService;
import org.ce.wp.service.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Parham Ahmadi
 * @since 27.06.23
 */
@Service
@RequiredArgsConstructor
public class EndpointServiceImpl implements EndpointService {
    private final UserService userService;
    private final UrlRepository urlRepository;
    private final QRCodeService qrCodeService;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public CreateUrlResponseDto creatEndpoint(CreateUrlRequestDto requestDto, String username) {
        User user = getUser(username);
        Url url = new Url();
        url.setUser(user);
        url.setUri(requestDto.uri());
        url.setCreationTime(new Date(System.currentTimeMillis()));
        url.setCount(0);
        url = urlRepository.save(url);
        String image = qrCodeService.generateQrCode(requestDto.uri());
        return new CreateUrlResponseDto(String.valueOf(url.getId()), image);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Url> findEndPoint(String username) {
        User user = getUser(username);
        return urlRepository.findByUser(user);
    }

    public String callUrl(String id) {
        Optional<Url> url = urlRepository.findById(Long.parseLong(id));
        if (url.isEmpty()) {
            return null;
        }
        url.get().setCount((url.get().getCount() + 1));
        urlRepository.save(url.get());
        return url.map(Url::getUri).orElse(null);
    }

    private User getUser(String username) {
        Optional<User> user = userService.getUser(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Username not found, username: " + username);
        }
        return user.get();
    }
}
