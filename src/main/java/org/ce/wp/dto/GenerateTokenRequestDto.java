package org.ce.wp.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;

/**
 * @author Parham Ahmadi
 * @since 22.01.23
 */
@Schema(title = "GenerateToken Request")
public record GenerateTokenRequestDto(@NotBlank String username, @NotBlank String password) {
}
