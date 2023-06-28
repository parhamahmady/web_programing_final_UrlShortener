package org.ce.wp.controller.advice;

import org.ce.wp.exception.CredentialsException;
import org.ce.wp.exception.InvalidJwtToken;
import org.ce.wp.exception.InvalidUrlIdException;
import org.ce.wp.exception.ShortenerInternalException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Parham Ahmadi
 * @since 27.06.23
 */
@Order(Integer.MIN_VALUE)
@ControllerAdvice
public class ExceptionHandlerInterceptor {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({ShortenerInternalException.class, Exception.class, Throwable.class, RuntimeException.class})
    public String internalExceptionHandler(Throwable e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "error.html";
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({CredentialsException.class, UsernameNotFoundException.class})
    public String credentialExceptionHandler(Throwable e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "error.html";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({InvalidJwtToken.class, InvalidUrlIdException.class})
    public String invalidInputExceptionHandler(InvalidJwtToken e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "error.html";
    }
}
