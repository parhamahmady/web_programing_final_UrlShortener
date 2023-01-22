package org.ce.wp.controller.advice;

import org.ce.wp.dto.ExceptionDto;
import org.ce.wp.exception.AlertEngineInternalException;
import org.ce.wp.exception.CredentialsException;
import org.ce.wp.exception.InvalidJwtToken;
import org.ce.wp.exception.InvalidUrlIdException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Parham Ahmadi
 * @since 23.01.23
 */
@RestControllerAdvice
public class ExceptionHandlerInterceptor {

    @ExceptionHandler(value = {AlertEngineInternalException.class, Exception.class})
    public ResponseEntity<ExceptionDto> internalExceptionHandler(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionDto(e.getMessage()));
    }

    @ExceptionHandler(value = {CredentialsException.class, UsernameNotFoundException.class})
    public ResponseEntity<ExceptionDto> credentialExceptionHandler(Exception e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ExceptionDto(e.getMessage()));
    }

    @ExceptionHandler(value = {InvalidJwtToken.class, InvalidUrlIdException.class})
    public ResponseEntity<ExceptionDto> invalidInputExceptionHandler(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionDto(e.getMessage()));
    }
}
