package org.ce.wp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.ce.wp.dto.GenerateTokenRequestDto;
import org.ce.wp.dto.GenerateTokenResponseDto;
import org.ce.wp.dto.SignUpRequestDto;
import org.ce.wp.dto.SignUpResponseDto;
import org.ce.wp.exception.CredentialsException;
import org.ce.wp.service.AAService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Parham Ahmadi
 * @since 20.01.23
 */
@RestController
@RequestMapping("/user")
@Tag(name = "User Controller")
@RequiredArgsConstructor
public class UserController {
    private final AAService aaService;

    @PostMapping(value = "/signUp")
    @Operation(summary = "Create User")
    @ApiResponse(responseCode = "200", description = "Successful Operation", content = @Content(schema = @Schema(implementation = SignUpResponseDto.class)))
    public SignUpResponseDto signUp(@Validated @RequestBody SignUpRequestDto request) {
        return aaService.signUpUser(request);
    }

    @PostMapping(value = "/token")
    @Operation(summary = "Generate Token For SignedUp User")
    @ApiResponse(responseCode = "200", description = "Successful Operation", content = @Content(schema = @Schema(implementation = GenerateTokenResponseDto.class)))
    public GenerateTokenResponseDto generateToken(@Validated @RequestBody GenerateTokenRequestDto requestDto) throws CredentialsException {
        return new GenerateTokenResponseDto(aaService.generateToken(requestDto));
    }
}
