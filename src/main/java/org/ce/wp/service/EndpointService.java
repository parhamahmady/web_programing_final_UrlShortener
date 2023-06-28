package org.ce.wp.service;

import org.ce.wp.dto.CreateUrlRequestDto;
import org.ce.wp.dto.CreateUrlResponseDto;
import org.ce.wp.entity.Url;

import java.util.List;

/**
 * @author Parham Ahmadi
 * @since 27.06.23
 */
public interface EndpointService {

    CreateUrlResponseDto creatEndpoint(CreateUrlRequestDto requestDto, String username);

    List<Url> findEndPoint(String username);

    String callUrl(String id);
}
