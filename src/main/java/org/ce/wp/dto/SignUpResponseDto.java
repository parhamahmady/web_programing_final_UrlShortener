package org.ce.wp.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author Parham Ahmadi
 * @since 27.06.23
 */
@Schema(title = "SignUp Response")
public record SignUpResponseDto(@Schema(title = "SignUp Result") boolean successful,
                                @Schema(title = "Jwt Token") String token) {
}
