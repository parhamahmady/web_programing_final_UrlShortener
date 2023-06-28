package org.ce.wp.controller;

import lombok.RequiredArgsConstructor;
import org.ce.wp.dto.CreateUrlRequestDto;
import org.ce.wp.dto.CreateUrlResponseDto;
import org.ce.wp.entity.Url;
import org.ce.wp.service.EndpointService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author Parham Ahmadi
 * @since 27.06.23
 */
@Controller
@RequestMapping("/url")
@RequiredArgsConstructor
public class EndpointController {
    private final EndpointService endpointService;

    @PostMapping("/create")
    public String createUrl(Model model, @ModelAttribute CreateUrlRequestDto requestDto, Authentication authentication) {
        CreateUrlResponseDto responseDto = endpointService.creatEndpoint(requestDto, ((User) authentication.getPrincipal()).getUsername());
        model.addAttribute("shortenedUrl", "/shortener/gw/" + responseDto.shortenedUrl());
        model.addAttribute("myImage", responseDto.qrCode());
        return "url/creationResponse.html";
    }

    @GetMapping("/create")
    public String createUrl(Model model) {
        model.addAttribute("request", new CreateUrlRequestDto(null));
        return "url/createUrl.html";
    }

    @GetMapping
    public String findUrl(Model model, Authentication authentication) {
        List<Url> urls = endpointService.findEndPoint(((User) authentication.getPrincipal()).getUsername());
        model.addAttribute("urls", urls);
        return "url/report.html";
    }
}
