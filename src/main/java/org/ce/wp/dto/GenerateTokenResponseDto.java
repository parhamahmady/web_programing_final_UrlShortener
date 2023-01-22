package org.ce.wp.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author Parham Ahmadi
 * @since 22.01.23
 */
@Schema(title = "Generate Token Response")
public record GenerateTokenResponseDto(@Schema(title = "Jwt Token") String token) {
}
