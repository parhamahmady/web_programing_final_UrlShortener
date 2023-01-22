package org.ce.wp.dto;

import org.ce.wp.entity.Url;

import java.util.List;

/**
 * @author Parham Ahmadi
 * @since 22.01.23
 */
public record FindUrlResponseDto(List<Url> urls) {
}
