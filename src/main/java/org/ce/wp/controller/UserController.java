
package org.ce.wp.controller;

import lombok.RequiredArgsConstructor;
import org.ce.wp.dto.LoginRequestDto;
import org.ce.wp.dto.SignUpRequestDto;
import org.ce.wp.dto.SignUpResponseDto;
import org.ce.wp.exception.CredentialsException;
import org.ce.wp.service.AAService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Parham Ahmadi
 * @since 27.06.23
 */
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final AAService aaService;

    @PostMapping(value = "/signUp")
    public String signUp(Model model, @ModelAttribute SignUpRequestDto request) throws CredentialsException {
        SignUpResponseDto signUpResponseDto = aaService.signUpUser(request);
        if (signUpResponseDto.successful()) {
            model.addAttribute("user", new LoginRequestDto(null, null));
            return "user/login.html";
        }
        model.addAttribute("user", new SignUpRequestDto(null, null, null));
        return "user/signUp_request.html";
    }

    @PostMapping(value = "/logIn")
    public String login(@ModelAttribute LoginRequestDto requestDto, HttpServletResponse response) throws CredentialsException {
        String token = aaService.login(requestDto);
        Cookie cookie = new Cookie("Authorization", "Bearer" + token);
        cookie.setPath("/");
        response.addCookie(cookie);
        return "desktop.html";
    }

    @GetMapping(value = "/logIn")
    public String getLoginPage(Model model) {
        model.addAttribute("user", new LoginRequestDto(null, null));
        return "user/login.html";
    }

    @GetMapping(value = "/signUp")
    public String getSignupPage(Model model) {
        model.addAttribute("user", new SignUpRequestDto(null, null, null));
        return "user/signUp_request.html";
    }

    @GetMapping("/reg/{id}")
    public String activate(@PathVariable String id) {
        aaService.activate(id);
        return "activate.html";
    }
}
