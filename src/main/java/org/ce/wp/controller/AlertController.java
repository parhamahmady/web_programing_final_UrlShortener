package org.ce.wp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.ce.wp.dto.AlertReportResponseDto;
import org.ce.wp.exception.CredentialsException;
import org.ce.wp.exception.InvalidUrlIdException;
import org.ce.wp.service.AlertService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Parham Ahmadi
 * @since 22.01.23
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/alert")
@Tag(name = "Alert Controller")
public class AlertController {
    private final AlertService alertService;

    @GetMapping("/{urlId}")
    @Operation(description = "Report Alert request status for specific url_id")
    @ApiResponse(responseCode = "200", description = "Successful Operation", content = @Content(schema = @Schema(implementation = AlertReportResponseDto.class)))
    public AlertReportResponseDto reportAlert(@PathVariable String urlId, Authentication authentication) throws CredentialsException, InvalidUrlIdException {
        return alertService.getAlertReport(urlId, ((User) authentication.getPrincipal()).getUsername());
    }
}
