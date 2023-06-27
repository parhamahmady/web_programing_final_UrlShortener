package org.ce.wp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.ce.wp.dto.CreateUrlRequestDto;
import org.ce.wp.dto.CreateUrlResponseDto;
import org.ce.wp.dto.FindUrlResponseDto;
import org.ce.wp.dto.UrlReportResponseDto;
import org.ce.wp.exception.CredentialsException;
import org.ce.wp.exception.InvalidUrlIdException;
import org.ce.wp.service.EndpointService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Parham Ahmadi
 * @since 27.06.23
 */
@RestController
@RequestMapping("/urls")
@RequiredArgsConstructor
@Tag(name = "Endpoints Controller")
public class EndpointController {
    private final EndpointService endpointService;

    @PostMapping
    @Operation(summary = "Create endpoint fot authorized user")
    @ApiResponse(responseCode = "200", description = "Successful Operation", content = @Content(schema = @Schema(implementation = CreateUrlResponseDto.class)))
    public CreateUrlResponseDto createUrl(@Validated @RequestBody CreateUrlRequestDto requestDto, Authentication authentication) {
        return endpointService.creatEndpoint(requestDto, ((User) authentication.getPrincipal()).getUsername());
    }

    @GetMapping
    @Operation(description = "Find all endpoints for authorized user")
    @ApiResponse(responseCode = "200", description = "Successful Operation", content = @Content(schema = @Schema(implementation = FindUrlResponseDto.class)))
    public FindUrlResponseDto findUrl(Authentication authentication) {
        return endpointService.findEndPoint(((User) authentication.getPrincipal()).getUsername());
    }

    @GetMapping("/{urlId}")
    @Operation(description = "Report request status for specific url_id")
    @ApiResponse(responseCode = "200", description = "Successful Operation", content = @Content(schema = @Schema(implementation = UrlReportResponseDto.class)))
    public UrlReportResponseDto reportUrl(@PathVariable String urlId, Authentication authentication) throws CredentialsException, InvalidUrlIdException {
        return endpointService.reportUrl(urlId, ((User) authentication.getPrincipal()).getUsername());
    }
}
