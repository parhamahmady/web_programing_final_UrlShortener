package org.ce.wp.dto;

import org.ce.wp.entity.Url;

/**
 * @author Parham Ahmadi
 * @since 27.06.23
 */
public record UrlReportResponseDto(int successfulCount, int unsuccessfulCount, Url url) {
}
