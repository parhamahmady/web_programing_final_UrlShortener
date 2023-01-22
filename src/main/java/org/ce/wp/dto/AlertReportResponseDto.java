package org.ce.wp.dto;

import org.ce.wp.entity.Url;

/**
 * @author Parham Ahmadi
 * @since 22.01.23
 */
public record AlertReportResponseDto(int alertCount, Url url) {
}
