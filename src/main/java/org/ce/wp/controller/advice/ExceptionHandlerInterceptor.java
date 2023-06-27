package org.ce.wp.controller.advice;

import org.ce.wp.dto.ExceptionDto;
import org.ce.wp.exception.AlertEngineInternalException;
import org.ce.wp.exception.CredentialsException;
import org.ce.wp.exception.InvalidJwtToken;
import org.ce.wp.exception.InvalidUrlIdException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Parham Ahmadi
 * @since 27.06.23
 */
@Order(Integer.MIN_VALUE)
@RestControllerAdvice
public class ExceptionHandlerInterceptor {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({AlertEngineInternalException.class, Exception.class, Throwable.class, RuntimeException.class})
    public ExceptionDto internalExceptionHandler(Throwable e) {
        return new ExceptionDto(e.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({CredentialsException.class, UsernameNotFoundException.class})
    public ExceptionDto credentialExceptionHandler(Throwable e) {
        return new ExceptionDto(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({InvalidJwtToken.class, InvalidUrlIdException.class})
    public ExceptionDto invalidInputExceptionHandler(InvalidJwtToken e) {
        return new ExceptionDto(e.getMessage());
    }
}
